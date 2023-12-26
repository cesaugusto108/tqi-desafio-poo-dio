package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.config.web.HEADER_KEY
import augusto108.ces.bootcamptracker.config.web.HEADER_VALUE
import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.model.datatypes.Name
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
import java.util.*
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

        val result: MvcResult = mockMvc.perform(
            post("${API_VERSION}instructors")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructor))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.age", `is`(38)))
            .andExpect(jsonPath("$.name.lastName", `is`("Campos")))
            .andExpect(jsonPath("$.email", `is`("fabiana@email.com")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}instructors")))
            .andReturn()

        val locationHeader: String? = result.response.getHeader("Location")
        val jsonResult: String = result.response.contentAsString
        val savedInstructor: Instructor = objectMapper.readerFor(Instructor::class.java).readValue(jsonResult)
        val uri = "http://localhost${API_VERSION}instructors/${savedInstructor.id}"
        assertEquals(locationHeader, uri)

        val pageInt: Int = Integer.parseInt(page)
        val maxInt: Int = Integer.parseInt(max)
        var pagedModel: PagedModel<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(pageInt, maxInt)
        assertEquals(4, pagedModel.content.size)
        val persistedInstructor: InstructorDTO? = pagedModel.content
            .stream()
            .filter { it.content?.email == "fabiana@email.com" }
            .findFirst().get().content
        instructorService.deleteInstructor(persistedInstructor?.id.toString())
        pagedModel = instructorService.findAllInstructors(pageInt, maxInt)
        assertEquals(3, pagedModel.content.size)
    }

    @Test
    @Order(1)
    fun findAllInstructors() {
        mockMvc.perform(
            get("${API_VERSION}instructors")
                .param("page", "0")
                .param("max", "10")
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string("X-Page-Number", "0"))
            .andExpect(header().string("X-Page-Size", "10"))
            .andExpect(jsonPath("$._embedded.instructorDTOList[0].id", `is`("4879ee9d-27d3-4b4b-a39c-1360d70d5a00")))
            .andExpect(jsonPath("$._embedded.instructorDTOList[0].age", `is`(22)))
            .andExpect(jsonPath("$._embedded.instructorDTOList[0].name.lastName", `is`("Pires")))
            .andExpect(jsonPath("$._embedded.instructorDTOList[0].email", `is`("osvaldo@email.com")))
            .andExpect(
                jsonPath(
                    "$._embedded.instructorDTOList[0]._links.self.href",
                    `is`("http://localhost${API_VERSION}instructors")
                )
            )
            .andExpect(
                jsonPath(
                    "$._embedded.instructorDTOList[0]._links.instructor4879ee9d-27d3-4b4b-a39c-1360d70d5a00.href",
                    `is`("http://localhost${API_VERSION}instructors/4879ee9d-27d3-4b4b-a39c-1360d70d5a00")
                )
            )
            .andExpect(jsonPath("$.page.size", `is`(10)))
            .andExpect(jsonPath("$.page.totalElements", `is`(10)))
            .andExpect(jsonPath("$.page.totalPages", `is`(1)))
            .andExpect(jsonPath("$.page.number", `is`(0)))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}instructors")
                .param("page", "0")
                .param("max", "10")
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.YAML))
            .andReturn()

        val yamlResponse: String = "email: \"osvaldo@email.com\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(2)
    fun findInstructorById() {
        mockMvc.perform(
            get("${API_VERSION}instructors/{id}", "4879ee9d-27d3-4b4b-a39c-1360d70d5a00")
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`("4879ee9d-27d3-4b4b-a39c-1360d70d5a00")))
            .andExpect(jsonPath("$.age", `is`(22)))
            .andExpect(jsonPath("$.name.lastName", `is`("Pires")))
            .andExpect(jsonPath("$.email", `is`("osvaldo@email.com")))
            .andExpect(
                jsonPath(
                    "$._links.self.href",
                    `is`("http://localhost${API_VERSION}instructors/4879ee9d-27d3-4b4b-a39c-1360d70d5a00")
                )
            )
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}instructors")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}instructors/{id}", "4879ee9d-27d3-4b4b-a39c-1360d70d5a00")
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.YAML))
            .andReturn()

        val yamlResponse: String = "email: \"osvaldo@email.com\""

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
                id = UUID.fromString("4879ee9d-27d3-4b4b-a39c-1360d70d5a04")
            )

        mockMvc.perform(
            put("${API_VERSION}instructors")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructor))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`("4879ee9d-27d3-4b4b-a39c-1360d70d5a04")))
            .andExpect(jsonPath("$.age", `is`(26)))
            .andExpect(jsonPath("$.name.lastName", `is`("Castro")))
            .andExpect(jsonPath("$.email", `is`("madalena@email.com")))
            .andExpect(jsonPath("$.username", `is`("madalenac")))
            .andExpect(
                jsonPath(
                    "$._links.self.href",
                    `is`("http://localhost${API_VERSION}instructors/4879ee9d-27d3-4b4b-a39c-1360d70d5a04")
                )
            )
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

        mockMvc.perform(
            delete("${API_VERSION}instructors/{id}", i.id)
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val pagedModel: PagedModel<EntityModel<InstructorDTO>> =
            instructorService.findAllInstructors(Integer.parseInt(page), Integer.parseInt(max))

        assertEquals(3, pagedModel.content.size)
    }

    @Test
    @Order(6)
    fun activateInstructor() {
        mockMvc.perform(
            patch("${API_VERSION}instructors/active/{ìd}", "4879ee9d-27d3-4b4b-a39c-1360d70d5a04")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val instructor: InstructorDTO = instructorService.findInstructorById("4879ee9d-27d3-4b4b-a39c-1360d70d5a04")

        assertTrue(instructor.active)
    }

    @Test
    @Order(7)
    fun deactivateInstructor() {
        mockMvc.perform(
            patch("${API_VERSION}instructors/inactive/{ìd}", "e8fd1a04-1c85-45e0-8f35-8ee8520e1800")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val instructor: InstructorDTO = instructorService.findInstructorById("e8fd1a04-1c85-45e0-8f35-8ee8520e1800")

        assertTrue(!instructor.active)
    }
}