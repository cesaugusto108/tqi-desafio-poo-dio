package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.dto.CourseDTO
import augusto108.ces.bootcamptracker.entities.Course
import augusto108.ces.bootcamptracker.services.CourseService
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
class CourseControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val courseService: CourseService
) : TestContainersConfig() {
    @Value("\${page.value}")
    var page: String = ""

    @Value("\${max.value}")
    var max: String = ""

    @Test
    @Order(4)
    fun saveCourse() {
        val course =
            Course(date = null, hours = 250, description = "POO", details = "Programação orientada a objetos com Java")

        mockMvc.perform(
            post("${API_VERSION}courses")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("POO")))
            .andExpect(jsonPath("$.details", `is`("Programação orientada a objetos com Java")))
            .andExpect(jsonPath("$.hours", `is`(250)))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}courses")))

        val courses: List<CourseDTO> = courseService.findAllCourses(Integer.parseInt(page), Integer.parseInt(max))
        courseService.deleteCourse(courses[2].id)
    }

    @Test
    @Order(1)
    fun findAllCourses() {
        mockMvc.perform(
            get("${API_VERSION}courses")
                .param("page", "0")
                .param("max", "10")
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].description", `is`("Sintaxe Kotlin")))
            .andExpect(jsonPath("$[0].details", `is`("Aprendendo a sintaxe Kotlin")))
            .andExpect(jsonPath("$[0].hours", `is`(300)))
            .andExpect(jsonPath("$[0].links[0].href", `is`("http://localhost${API_VERSION}courses")))
            .andExpect(jsonPath("$[0].links[1].href", `is`("http://localhost${API_VERSION}courses/-4")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}courses")
                .param("page", "0")
                .param("max", "10")
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "---\n" +
                "- id: -4\n" +
                "  description: \"Sintaxe Kotlin\"\n" +
                "  details: \"Aprendendo a sintaxe Kotlin\"\n" +
                "  hours: 300"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(2)
    fun findCourseById() {
        mockMvc.perform(get("${API_VERSION}courses/{id}", -4).header(HEADER_KEY, HEADER_VALUE))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.description", `is`("Sintaxe Kotlin")))
            .andExpect(jsonPath("$.details", `is`("Aprendendo a sintaxe Kotlin")))
            .andExpect(jsonPath("$.hours", `is`(300)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}courses/-4")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}courses")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}courses/{id}", -4)
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "id: -4\n" +
                "description: \"Sintaxe Kotlin\"\n" +
                "details: \"Aprendendo a sintaxe Kotlin\"\n" +
                "hours: 300"

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(3)
    fun updateCourse() {
        val course =
            Course(
                date = null,
                hours = 250,
                description = "MySQL",
                details = "Banco de dados relacional com MySQL",
                id = -3
            )

        mockMvc.perform(
            put("${API_VERSION}courses")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(course))
        )
            .andExpect(jsonPath("$.description", `is`("MySQL")))
            .andExpect(jsonPath("$.details", `is`("Banco de dados relacional com MySQL")))
            .andExpect(jsonPath("$.id", `is`(-3)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}courses/-3")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}courses")))
    }

    @Test
    @Order(5)
    fun deleteCourse() {
        val course = Course(date = null, hours = 250, description = "Docker", details = "Docker containers")

        val c: CourseDTO = courseService.saveCourse(course)

        mockMvc.perform(
            delete("${API_VERSION}courses/{id}", c.id)
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val courses: List<CourseDTO> = courseService.findAllCourses(Integer.parseInt(page), Integer.parseInt(max))

        assertEquals(2, courses.size)
    }
}