package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.entities.Mentoring
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
class MentoringControllerTest(
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
        val mentoringQuery: String =
            "insert into " +
                    "`activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)" +
                    " values ('mentoring', -1, 'Orientação a objetos', 'Orientação a objetos com Kotlin', NULL, NULL);"

        entityManager?.createNativeQuery(mentoringQuery, Mentoring::class.java)?.executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager?.createNativeQuery("delete from `activity`;")
    }

    @Test
    fun saveMentoring() {
        val mentoring =
            Mentoring(date = null, hours = null, description = "REST APIs", details = "REST APIs com Spring e Kotlin")

        mockMvc.perform(
            post("/mentoring").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentoring))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("REST APIs")))
            .andExpect(jsonPath("$.details", `is`("REST APIs com Spring e Kotlin")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost/mentoring")))
    }

    @Test
    fun findAllMentoring() {
        mockMvc.perform(get("/mentoring").param("page", "0").param("max", "10"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].description", `is`("Orientação a objetos")))
            .andExpect(jsonPath("$[0].details", `is`("Orientação a objetos com Kotlin")))
            .andExpect(jsonPath("$[0].links[0].href", `is`("http://localhost/mentoring")))
            .andExpect(jsonPath("$[0].links[1].href", `is`("http://localhost/mentoring/-1")))

        val result: MvcResult = mockMvc.perform(
            get("/mentoring")
                .param("page", "0")
                .param("max", "10")
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "- id: -1"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    fun findMentoringById() {
        mockMvc.perform(get("/mentoring/{id}", -1))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("Orientação a objetos")))
            .andExpect(jsonPath("$.details", `is`("Orientação a objetos com Kotlin")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost/mentoring/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost/mentoring")))

        val result: MvcResult = mockMvc.perform(
            get("/mentoring/{id}", -1)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "id: -1"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
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
            put("/mentoring").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(mentoring))
        )
            .andExpect(jsonPath("$.description", `is`("Android apps")))
            .andExpect(jsonPath("$.details", `is`("Kotlin e apps para Android")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost/mentoring/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost/mentoring")))
    }

    @Test
    fun deleteMentoring() {
        mockMvc.perform(delete("/mentoring/{id}", -1).with(csrf()))
            .andExpect(status().isNoContent)

        val mentoringList: MutableList<Mentoring>? = entityManager
            ?.createQuery("from Mentoring order by id", Mentoring::class.java)
            ?.resultList

        assertEquals(0, mentoringList?.size)
    }
}