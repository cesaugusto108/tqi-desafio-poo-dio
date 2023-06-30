package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.Instructor
import augusto108.ces.bootcamptracker.model.Name
import augusto108.ces.bootcamptracker.model.Person
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.context.ActiveProfiles
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
class InstructorControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    @BeforeEach
    fun setUp() {
        val instructorQuery: String =
            "insert into " +
                    "`person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `developer_level`)" +
                    " values ('instructor', -2, 32, 'maria@email.com', 'Maria', 'Souza', '', NULL);"

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
    }

    @Test
    fun updateInstructor() {
        val instructor =
            Instructor(
                name = Name(firstName = "Madalena", lastName = "Castro"),
                email = "madalena@email.com",
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