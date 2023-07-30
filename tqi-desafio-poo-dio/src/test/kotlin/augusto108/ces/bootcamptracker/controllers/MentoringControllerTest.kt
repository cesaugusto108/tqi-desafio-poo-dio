package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.dto.MentoringDTO
import augusto108.ces.bootcamptracker.entities.Mentoring
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

        mockMvc.perform(
            post("${API_VERSION}mentoring").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentoring))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("REST APIs")))
            .andExpect(jsonPath("$.details", `is`("REST APIs com Spring e Kotlin")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}mentoring")))

        val mentoringList: List<MentoringDTO> =
            mentoringService.findAllMentoring(Integer.parseInt(page), Integer.parseInt(max))
        mentoringService.deleteMentoring(mentoringList[1].id)
    }

    @Test
    @Order(1)
    fun findAllMentoring() {
        mockMvc.perform(get("${API_VERSION}mentoring").param("page", "0").param("max", "10"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].description", `is`("Java - POO")))
            .andExpect(jsonPath("$[0].details", `is`("Orientação a objetos com Java")))
            .andExpect(jsonPath("$[0].links[0].href", `is`("http://localhost${API_VERSION}mentoring")))
            .andExpect(jsonPath("$[0].links[1].href", `is`("http://localhost${API_VERSION}mentoring/-2")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}mentoring")
                .param("page", "0")
                .param("max", "10")
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "- id: -2"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(2)
    fun findMentoringById() {
        mockMvc.perform(get("${API_VERSION}mentoring/{id}", -2))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("Java - POO")))
            .andExpect(jsonPath("$.details", `is`("Orientação a objetos com Java")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}mentoring/-2")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}mentoring")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}mentoring/{id}", -2)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
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
            put("${API_VERSION}mentoring").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentoring))
        )
            .andExpect(jsonPath("$.description", `is`("Android apps")))
            .andExpect(jsonPath("$.details", `is`("Kotlin e apps para Android")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}mentoring/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}mentoring")))
    }

    @Test
    @Order(5)
    fun deleteMentoring() {
        val mentoring =
            Mentoring(date = null, hours = null, description = "React JS", details = "Frontend with React")

        val m: MentoringDTO = mentoringService.saveMentoring(mentoring)

        mockMvc.perform(delete("${API_VERSION}mentoring/{id}", m.id).with(csrf()))
            .andExpect(status().isNoContent)

        val mentoringList: List<MentoringDTO> =
            mentoringService.findAllMentoring(Integer.parseInt(page), Integer.parseInt(max))

        assertEquals(2, mentoringList.size)
    }
}