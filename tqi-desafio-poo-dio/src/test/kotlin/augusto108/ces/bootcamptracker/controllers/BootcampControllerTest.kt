package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.Bootcamp
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.transaction.annotation.Transactional

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
            post("/bootcamps").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bootcamp))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("Java backend")))
            .andExpect(jsonPath("$.details", `is`("Java and Spring backend")))
    }

    @Test
    fun findAllBootcamps() {
        mockMvc.perform(get("/bootcamps").param("page", page).param("max", max))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].description", `is`("TQI Kotlin Backend")))
            .andExpect(jsonPath("$[0].details", `is`("Java e Kotlin backend")))
    }

    @Test
    fun findBootcampById() {
        mockMvc.perform(get("/bootcamps/{id}", -1))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("TQI Kotlin Backend")))
            .andExpect(jsonPath("$.details", `is`("Java e Kotlin backend")))
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
            put("/bootcamps").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bootcamp))
        )
            .andExpect(jsonPath("$.description", `is`("Go backend")))
            .andExpect(jsonPath("$.details", `is`("Go backend development")))
            .andExpect(jsonPath("$.id", `is`(-1)))
    }

    @Test
    fun deleteBootcamp() {
        mockMvc.perform(delete("/bootcamps/{id}", -1).with(csrf()))
            .andExpect(status().isNoContent)

        val bootcamps: MutableList<Bootcamp>? = entityManager
            ?.createQuery("from Bootcamp order by id", Bootcamp::class.java)
            ?.resultList

        assertEquals(0, bootcamps?.size)
    }
}