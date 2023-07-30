package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.dto.InstructorDTO
import augusto108.ces.bootcamptracker.entities.Instructor
import augusto108.ces.bootcamptracker.entities.Name
import augusto108.ces.bootcamptracker.services.InstructorService
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
class InstructorControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val instructorService: InstructorService
) : TestContainersConfig() {
    @Value("\${page.value}")
    var page: String = ""

    @Value("\${max.value}")
    var max: String = ""

    @Test
    @Order(4)
    fun saveInstructor() {
        val instructor =
            Instructor(name = Name(firstName = "Fabiana", lastName = "Campos"), email = "fabiana@email.com", age = 38)

        mockMvc.perform(
            post("${API_VERSION}instructors").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructor))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.age", `is`(38)))
            .andExpect(jsonPath("$.name.lastName", `is`("Campos")))
            .andExpect(jsonPath("$.email", `is`("fabiana@email.com")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}instructors")))

        val instructors: List<InstructorDTO> =
            instructorService.findAllInstructors(Integer.parseInt(page), Integer.parseInt(max))
        instructorService.deleteInstructor(instructors[1].id)
    }

    @Test
    @Order(1)
    fun findAllInstructors() {
        mockMvc.perform(get("${API_VERSION}instructors").param("page", "0").param("max", "10"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id", `is`(-4)))
            .andExpect(jsonPath("$[0].age", `is`(32)))
            .andExpect(jsonPath("$[0].name.lastName", `is`("Santos")))
            .andExpect(jsonPath("$[0].email", `is`("florinda@email.com")))
            .andExpect(jsonPath("$[0].links[0].href", `is`("http://localhost${API_VERSION}instructors")))
            .andExpect(jsonPath("$[0].links[1].href", `is`("http://localhost${API_VERSION}instructors/-4")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}instructors")
                .param("page", "0")
                .param("max", "10")
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "email: \"florinda@email.com\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(2)
    fun findInstructorById() {
        mockMvc.perform(get("${API_VERSION}instructors/{id}", -4))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(-4)))
            .andExpect(jsonPath("$.age", `is`(32)))
            .andExpect(jsonPath("$.name.lastName", `is`("Santos")))
            .andExpect(jsonPath("$.email", `is`("florinda@email.com")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}instructors/-4")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}instructors")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}instructors/{id}", -4)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "email: \"florinda@email.com\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(3)
    fun updateInstructor() {
        val instructor =
            Instructor(
                name = Name(firstName = "Madalena", lastName = "Castro"),
                email = "madalena@email.com",
                username = "madalenac",
                age = 26,
                id = -4
            )

        mockMvc.perform(
            put("${API_VERSION}instructors").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructor))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(-4)))
            .andExpect(jsonPath("$.age", `is`(26)))
            .andExpect(jsonPath("$.name.lastName", `is`("Castro")))
            .andExpect(jsonPath("$.email", `is`("madalena@email.com")))
            .andExpect(jsonPath("$.username", `is`("madalenac")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}instructors/-4")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}instructors")))
    }

    @Test
    @Order(5)
    fun deleteInstructor() {
        val instructor =
            Instructor(
                name = Name(firstName = "Milena", lastName = "Andrade"),
                email = "milena@email.com",
                username = "milenaa",
                age = 26
            )

        val i: InstructorDTO = instructorService.saveInstructor(instructor)

        mockMvc.perform(delete("${API_VERSION}instructors/{id}", i.id).with(csrf()))
            .andExpect(status().isNoContent)

        val instructors: List<InstructorDTO> =
            instructorService.findAllInstructors(Integer.parseInt(page), Integer.parseInt(max))

        assertEquals(2, instructors.size)
    }
}