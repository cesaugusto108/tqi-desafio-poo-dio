package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.config.web.HEADER_KEY
import augusto108.ces.bootcamptracker.config.web.HEADER_VALUE
import augusto108.ces.bootcamptracker.model.dto.MentoringDTO
import augusto108.ces.bootcamptracker.model.entities.Mentoring
import augusto108.ces.bootcamptracker.services.MentoringService
import augusto108.ces.bootcamptracker.util.API_VERSION
import com.fasterxml.jackson.databind.ObjectMapper
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import augusto108.ces.bootcamptracker.util.MediaType as UtilMediaType

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class MentoringControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val mentoringService: MentoringService
) : TestContainersConfig() {

    @Value("\${page.value}")
    var page: String = ""

    @Value("\${max.value}")
    var max: String = ""

    @Test
    @Order(4)
    fun saveMentoring() {
        val mentoring =
            Mentoring(date = null, hours = null, description = "REST APIs", details = "REST APIs com Spring e Kotlin")

        val result: MvcResult = mockMvc.perform(
            post("${API_VERSION}mentoring")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentoring))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("REST APIs")))
            .andExpect(jsonPath("$.details", `is`("REST APIs com Spring e Kotlin")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}mentoring")))
            .andReturn()

        val locationHeader: String? = result.response.getHeader("Location")
        val jsonResult: String = result.response.contentAsString
        val savedMentoring: Mentoring = objectMapper.readerFor(Mentoring::class.java).readValue(jsonResult)
        val uri = "http://localhost${API_VERSION}mentoring/${savedMentoring.id}"
        assertEquals(locationHeader, uri)

        val pageInt: Int = Integer.parseInt(page)
        val maxInt: Int = Integer.parseInt(max)
        var pagedModel: PagedModel<EntityModel<MentoringDTO>> = mentoringService.findAllMentoring(pageInt, maxInt)
        assertEquals(3, pagedModel.content.size)
        mentoringService.deleteMentoring(pagedModel.content.toList()[1].content?.id!!)
        pagedModel = mentoringService.findAllMentoring(pageInt, maxInt)
        assertEquals(2, pagedModel.content.size)
    }

    @Test
    @Order(1)
    fun findAllMentoring() {
        mockMvc.perform(
            get("${API_VERSION}mentoring")
                .param("page", "0")
                .param("max", "10")
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string("X-Page-Number", "0"))
            .andExpect(header().string("X-Page-Size", "10"))
            .andExpect(jsonPath("$._embedded.mentoringDTOList[0].description", `is`("Java - POO")))
            .andExpect(jsonPath("$._embedded.mentoringDTOList[0].details", `is`("Orientação a objetos com Java")))
            .andExpect(
                jsonPath(
                    "$._embedded.mentoringDTOList[0]._links.self.href",
                    `is`("http://localhost${API_VERSION}mentoring")
                )
            )
            .andExpect(
                jsonPath(
                    "$._embedded.mentoringDTOList[0]._links.mentoring-2.href",
                    `is`("http://localhost${API_VERSION}mentoring/-2")
                )
            )
            .andExpect(jsonPath("$.page.size", `is`(10)))
            .andExpect(jsonPath("$.page.totalElements", `is`(10)))
            .andExpect(jsonPath("$.page.totalPages", `is`(1)))
            .andExpect(jsonPath("$.page.number", `is`(0)))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}mentoring")
                .param("page", "0")
                .param("max", "10")
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.YAML))
            .andReturn()

        val yamlResponse: String = "- id: -2"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(2)
    fun findMentoringById() {
        mockMvc.perform(get("${API_VERSION}mentoring/{id}", -2).header(HEADER_KEY, HEADER_VALUE))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("Java - POO")))
            .andExpect(jsonPath("$.details", `is`("Orientação a objetos com Java")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}mentoring/-2")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}mentoring")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}mentoring/{id}", -2)
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.YAML))
            .andReturn()

        val yamlResponse: String = "id: -2"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(3)
    fun updateMentoring() {
        val mentoring =
            Mentoring(
                date = null,
                hours = null,
                description = "Android apps",
                details = "Kotlin e apps para Android",
                id = -1
            )

        mockMvc.perform(
            put("${API_VERSION}mentoring/{id}", -1)
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentoring))
        )
            .andExpect(jsonPath("$.description", `is`("Android apps")))
            .andExpect(jsonPath("$.details", `is`("Kotlin e apps para Android")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}mentoring/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}mentoring")))

        mockMvc.perform(
            put("${API_VERSION}mentoring/{id}", -2)
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentoring))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message", `is`("Path id and request body id do not match")))
            .andExpect(jsonPath("$.status", `is`("BAD_REQUEST")))
            .andExpect(jsonPath("$.statusCode", `is`(400)))
    }

    @Test
    @Order(5)
    fun deleteMentoring() {
        val mentoring =
            Mentoring(date = null, hours = null, description = "React JS", details = "Frontend with React")

        val m: MentoringDTO = mentoringService.saveMentoring(mentoring)

        mockMvc.perform(
            delete("${API_VERSION}mentoring/{id}", m.id)
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val pagedModel: PagedModel<EntityModel<MentoringDTO>> =
            mentoringService.findAllMentoring(Integer.parseInt(page), Integer.parseInt(max))

        assertEquals(2, pagedModel.content.size)
    }
}