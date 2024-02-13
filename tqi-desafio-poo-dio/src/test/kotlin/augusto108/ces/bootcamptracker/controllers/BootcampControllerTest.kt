package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.config.web.HEADER_KEY
import augusto108.ces.bootcamptracker.config.web.HEADER_VALUE
import augusto108.ces.bootcamptracker.model.dto.BootcampDTO
import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.services.BootcampService
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
class BootcampControllerTest @Autowired constructor(
    private val mockMvc: MockMvc,
    private val objectMapper: ObjectMapper,
    private val bootcampService: BootcampService
) : TestContainersConfig() {

    @Value("\${page.value}")
    var page: String = ""

    @Value("\${max.value}")
    var max: String = ""

    @Test
    @Order(4)
    fun saveBootcamp() {
        val bootcamp = Bootcamp(
            description = "Java backend",
            details = "Java and Spring backend",
            startDate = null,
            finishDate = null
        )

        val result: MvcResult = mockMvc.perform(
            post("${API_VERSION}bootcamps")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bootcamp))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("Java backend")))
            .andExpect(jsonPath("$.details", `is`("Java and Spring backend")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}bootcamps")))
            .andReturn()

        val locationHeader: String? = result.response.getHeader("Location")
        val jsonResult: String = result.response.contentAsString
        val savedBootcamp: Bootcamp = objectMapper.readerFor(Bootcamp::class.java).readValue(jsonResult)
        val uri = "http://localhost${API_VERSION}bootcamps/${savedBootcamp.id}"
        assertEquals(locationHeader, uri)

        val pageInt: Int = Integer.parseInt(page)
        val maxInt: Int = Integer.parseInt(max)
        var pagedModel: PagedModel<EntityModel<BootcampDTO>> = bootcampService.findAllBootcamps(pageInt, maxInt)
        assertEquals(4, pagedModel.content.size)
        bootcampService.deleteBootcamp(pagedModel.content.toList()[3].content?.id!!)
        pagedModel = bootcampService.findAllBootcamps(pageInt, maxInt)
        assertEquals(3, pagedModel.content.size)
    }

    @Test
    @Order(1)
    fun findAllBootcamps() {
        mockMvc.perform(
            get("${API_VERSION}bootcamps")
                .param("page", page)
                .param("max", max)
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string("X-Page-Number", "0"))
            .andExpect(header().string("X-Page-Size", "10"))
            .andExpect(jsonPath("$._embedded.bootcampDTOList[1].description", `is`("Linux Experience")))
            .andExpect(jsonPath("$._embedded.bootcampDTOList[1].details", `is`("Aperfeiçoamento Linux")))
            .andExpect(
                jsonPath(
                    "$._embedded.bootcampDTOList[1]._links.self.href",
                    `is`("http://localhost${API_VERSION}bootcamps")
                )
            )
            .andExpect(
                jsonPath(
                    "$._embedded.bootcampDTOList[1]._links.bootcamp-2.href",
                    `is`("http://localhost${API_VERSION}bootcamps/-2")
                )
            )
            .andExpect(jsonPath("$.page.size", `is`(10)))
            .andExpect(jsonPath("$.page.totalElements", `is`(3)))
            .andExpect(jsonPath("$.page.totalPages", `is`(1)))
            .andExpect(jsonPath("$.page.number", `is`(0)))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}bootcamps")
                .param("page", page)
                .param("max", max)
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.YAML))
            .andReturn()

        val yamlResponse: String = "- id: -3\n" +
                "  description: \"AWS Experience\"\n" +
                "  details: \"Cloud Computing\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(2)
    fun findBootcampById() {
        mockMvc.perform(get("${API_VERSION}bootcamps/{id}", -1).header(HEADER_KEY, HEADER_VALUE))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("TQI Kotlin Backend")))
            .andExpect(jsonPath("$.details", `is`("Java e Kotlin backend")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}bootcamps/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}bootcamps")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}bootcamps/{id}", -1)
                .accept(MediaType.valueOf(UtilMediaType.YAML))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.YAML))
            .andReturn()

        val yamlResponse: String = "---\n" +
                "id: -1\n" +
                "description: \"TQI Kotlin Backend\"\n" +
                "details: \"Java e Kotlin backend\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(3)
    fun updateBootcamp() {
        val bootcamp = Bootcamp(
            description = "Go backend",
            details = "Go backend development",
            startDate = null,
            finishDate = null,
            id = -1
        )

        mockMvc.perform(
            put("${API_VERSION}bootcamps/{id}", -1)
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bootcamp))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.description", `is`("Go backend")))
            .andExpect(jsonPath("$.details", `is`("Go backend development")))
            .andExpect(jsonPath("$.id", `is`(-1)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}bootcamps/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}bootcamps")))

        mockMvc.perform(
            put("${API_VERSION}bootcamps/{id}", -2)
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bootcamp))
        )
            .andExpect(status().isBadRequest)
            .andExpect(jsonPath("$.message", `is`("Path id and request body id do not match")))
            .andExpect(jsonPath("$.status", `is`("BAD_REQUEST")))
            .andExpect(jsonPath("$.statusCode", `is`(400)))
    }

    @Test
    @Order(5)
    fun deleteBootcamp() {
        val bootcamp = Bootcamp(
            description = "NodeJS",
            details = "NodeJS backend development",
            startDate = null,
            finishDate = null
        )

        val b: BootcampDTO = bootcampService.saveBootcamp(bootcamp)

        mockMvc.perform(
            delete("${API_VERSION}bootcamps/{id}", b.id)
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val pagedModel: PagedModel<EntityModel<BootcampDTO>> =
            bootcampService.findAllBootcamps(Integer.parseInt(page), Integer.parseInt(max))

        assertEquals(3, pagedModel.content.toList().size)
    }
}