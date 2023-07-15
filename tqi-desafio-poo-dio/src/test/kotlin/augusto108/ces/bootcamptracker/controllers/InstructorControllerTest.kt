package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.entities.Instructor
import augusto108.ces.bootcamptracker.entities.Name
import augusto108.ces.bootcamptracker.entities.Person
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
class InstructorControllerTest(
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
        val instructorQuery: String =
            "insert into " +
                    "`person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)" +
                    " values ('instructor', -2, 32, 'maria@email.com', 'Maria', 'Souza', '', '1234', 'marias', NULL);"

        entityManager?.createNativeQuery(instructorQuery, Person::class.java)?.executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager?.createNativeQuery("delete from `person`;")
    }

    @Test
    fun saveInstructor() {
        val instructor =
            Instructor(name = Name(firstName = "Fabiana", lastName = "Campos"), email = "fabiana@email.com", age = 38)

        mockMvc.perform(
            post("/instructors").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructor))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.age", `is`(38)))
            .andExpect(jsonPath("$.name.lastName", `is`("Campos")))
            .andExpect(jsonPath("$.email", `is`("fabiana@email.com")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost/instructors")))
    }

    @Test
    fun findAllInstructors() {
        mockMvc.perform(get("/instructors").param("page", "0").param("max", "10"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id", `is`(-2)))
            .andExpect(jsonPath("$[0].age", `is`(32)))
            .andExpect(jsonPath("$[0].name.lastName", `is`("Souza")))
            .andExpect(jsonPath("$[0].email", `is`("maria@email.com")))
            .andExpect(jsonPath("$[0].links[0].href", `is`("http://localhost/instructors")))
            .andExpect(jsonPath("$[0].links[1].href", `is`("http://localhost/instructors/-2")))

        val result: MvcResult = mockMvc.perform(
            get("/instructors")
                .param("page", "0")
                .param("max", "10")
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "email: \"maria@email.com\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    fun findInstructorById() {
        mockMvc.perform(get("/instructors/{id}", -2))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(-2)))
            .andExpect(jsonPath("$.age", `is`(32)))
            .andExpect(jsonPath("$.name.lastName", `is`("Souza")))
            .andExpect(jsonPath("$.email", `is`("maria@email.com")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost/instructors/-2")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost/instructors")))

        val result: MvcResult = mockMvc.perform(
            get("/instructors/{id}", -2)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "email: \"maria@email.com\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    fun updateInstructor() {
        val instructor =
            Instructor(
                name = Name(firstName = "Madalena", lastName = "Castro"),
                email = "madalena@email.com",
                username = "madalenac",
                age = 26,
                id = -2
            )

        mockMvc.perform(
            put("/instructors").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(instructor))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(-2)))
            .andExpect(jsonPath("$.age", `is`(26)))
            .andExpect(jsonPath("$.name.lastName", `is`("Castro")))
            .andExpect(jsonPath("$.email", `is`("madalena@email.com")))
            .andExpect(jsonPath("$.username", `is`("madalenac")))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost/instructors/-2")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost/instructors")))
    }

    @Test
    fun deleteInstructor() {
        mockMvc.perform(delete("/instructors/{id}", -2).with(csrf()))
            .andExpect(status().isNoContent)

        val instructors: MutableList<Instructor>? = entityManager
            ?.createQuery("from Instructor i order by id", Instructor::class.java)
            ?.resultList

        assertEquals(0, instructors?.size)
    }
}