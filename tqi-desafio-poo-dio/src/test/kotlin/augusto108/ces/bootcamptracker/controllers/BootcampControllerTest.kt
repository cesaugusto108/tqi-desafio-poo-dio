package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.entities.Bootcamp
import augusto108.ces.bootcamptracker.util.API_VERSION
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
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
import org.springframework.transaction.annotation.Transactional
import augusto108.ces.bootcamptracker.util.MediaType as UtilMediaType

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
class BootcampControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    @Value("\${page.value}")
    var page: String = ""

    @Value("\${max.value}")
    var max: String = ""

    @BeforeEach
    fun setUp() {
        val bootcampQuery: String =
            "insert into " +
                    "`bootcamp` (`id`, `bootcamp_description`, `bootcamp_details`, `finish_date`, `start_date`)" +
                    " values (-1, 'TQI Kotlin Backend', 'Java e Kotlin backend', NULL, NULL);"

        entityManager?.createNativeQuery(bootcampQuery, Bootcamp::class.java)?.executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager?.createNativeQuery("delete from `bootcamp`;")
    }

    @Test
    fun saveBootcamp() {
        val bootcamp = Bootcamp(
            description = "Java backend",
            details = "Java and Spring backend",
            startDate = null,
            finishDate = null
        )

        mockMvc.perform(
            post("${API_VERSION}bootcamps").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bootcamp))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("Java backend")))
            .andExpect(jsonPath("$.details", `is`("Java and Spring backend")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}bootcamps")))
    }

    @Test
    fun findAllBootcamps() {
        mockMvc.perform(
            get("${API_VERSION}bootcamps")
                .param("page", page)
                .param("max", max)
                .accept(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].description", `is`("TQI Kotlin Backend")))
            .andExpect(jsonPath("$[0].details", `is`("Java e Kotlin backend")))
            .andExpect(jsonPath("$[0].links[0].href", `is`("http://localhost${API_VERSION}bootcamps")))
            .andExpect(jsonPath("$[0].links[1].href", `is`("http://localhost${API_VERSION}bootcamps/-1")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}bootcamps")
                .param("page", page)
                .param("max", max)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "- id: -1\n" +
                "  description: \"TQI Kotlin Backend\"\n" +
                "  details: \"Java e Kotlin backend\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    fun findBootcampById() {
        mockMvc.perform(get("${API_VERSION}bootcamps/{id}", -1))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("TQI Kotlin Backend")))
            .andExpect(jsonPath("$.details", `is`("Java e Kotlin backend")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}bootcamps/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}bootcamps")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}bootcamps/{id}", -1)
                .accept(MediaType.valueOf(UtilMediaType.APPLICATION_YAML))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "---\n" +
                "id: -1\n" +
                "description: \"TQI Kotlin Backend\"\n" +
                "details: \"Java e Kotlin backend\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    fun updateBootcamp() {
        val bootcamp = Bootcamp(
            description = "Go backend",
            details = "Go backend development",
            startDate = null,
            finishDate = null,
            id = -1
        )

        mockMvc.perform(
            put("${API_VERSION}bootcamps").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bootcamp))
        )
            .andExpect(jsonPath("$.description", `is`("Go backend")))
            .andExpect(jsonPath("$.details", `is`("Go backend development")))
            .andExpect(jsonPath("$.id", `is`(-1)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}bootcamps/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}bootcamps")))
    }

    @Test
    fun deleteBootcamp() {
        mockMvc.perform(delete("${API_VERSION}bootcamps/{id}", -1).with(csrf()))
            .andExpect(status().isNoContent)

        val bootcamps: MutableList<Bootcamp>? = entityManager
            ?.createQuery("from Bootcamp order by id", Bootcamp::class.java)
            ?.resultList

        assertEquals(0, bootcamps?.size)
    }
}