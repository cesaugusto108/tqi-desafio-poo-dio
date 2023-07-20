package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.entities.Course
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
class CourseControllerTest(
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
        val courseQuery: String =
            "insert into " +
                    "`activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)" +
                    " values ('course', -2, 'Sintaxe Java', 'Aprendendo a sintaxe Java', NULL, 300);"

        entityManager?.createNativeQuery(courseQuery, Course::class.java)?.executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager?.createNativeQuery("delete from `activity`;")
    }

    @Test
    fun saveCourse() {
        val course =
            Course(date = null, hours = 250, description = "POO", details = "Programação orientada a objetos com Java")

        mockMvc.perform(
            post("${API_VERSION}courses").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("POO")))
            .andExpect(jsonPath("$.details", `is`("Programação orientada a objetos com Java")))
            .andExpect(jsonPath("$.hours", `is`(250)))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}courses")))
    }

    @Test
    fun findAllCourses() {
        mockMvc.perform(get("${API_VERSION}courses").param("page", "0").param("max", "10"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].description", `is`("Sintaxe Java")))
            .andExpect(jsonPath("$[0].details", `is`("Aprendendo a sintaxe Java")))
            .andExpect(jsonPath("$[0].hours", `is`(300)))
            .andExpect(jsonPath("$[0].links[0].href", `is`("http://localhost${API_VERSION}courses")))
            .andExpect(jsonPath("$[0].links[1].href", `is`("http://localhost${API_VERSION}courses/-2")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}courses")
                .param("page", "0")
                .param("max", "10")
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "---\n" +
                "- id: -2\n" +
                "  description: \"Sintaxe Java\"\n" +
                "  details: \"Aprendendo a sintaxe Java\"\n" +
                "  hours: 300"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    fun findCourseById() {
        mockMvc.perform(get("${API_VERSION}courses/{id}", -2))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("Sintaxe Java")))
            .andExpect(jsonPath("$.details", `is`("Aprendendo a sintaxe Java")))
            .andExpect(jsonPath("$.hours", `is`(300)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}courses/-2")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}courses")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}courses/{id}", -2)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "id: -2\n" +
                "description: \"Sintaxe Java\"\n" +
                "details: \"Aprendendo a sintaxe Java\"\n" +
                "hours: 300"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    fun updateCourse() {
        val course =
            Course(
                date = null,
                hours = 250,
                description = "MySQL",
                details = "Banco de dados relacional com MySQL",
                id = -2
            )

        mockMvc.perform(
            put("${API_VERSION}courses").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course))
        )
            .andExpect(jsonPath("$.description", `is`("MySQL")))
            .andExpect(jsonPath("$.details", `is`("Banco de dados relacional com MySQL")))
            .andExpect(jsonPath("$.id", `is`(-2)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}courses/-2")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}courses")))
    }

    @Test
    fun deleteCourse() {
        mockMvc.perform(delete("${API_VERSION}courses/{id}", -2).with(csrf()))
            .andExpect(status().isNoContent)

        val courses: MutableList<Course>? = entityManager
            ?.createQuery("from Course order by id", Course::class.java)
            ?.resultList

        assertEquals(0, courses?.size)
    }
}