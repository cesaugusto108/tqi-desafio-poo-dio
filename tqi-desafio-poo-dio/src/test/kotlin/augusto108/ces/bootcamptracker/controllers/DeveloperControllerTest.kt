package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.Developer
import augusto108.ces.bootcamptracker.model.Name
import augusto108.ces.bootcamptracker.model.Person
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.hamcrest.Matchers.`is`
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
class DeveloperControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {
    @PersistenceContext
    private val entityManager: EntityManager? = null

    @BeforeEach
    fun setUp() {
        val developerQuery: String =
            "insert into " +
                    "`person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `developer_level`)" +
                    " values ('developer', -1, 29, 'josecc@email.com', 'José', 'Costa', 'Carlos', 2);"

        entityManager?.createNativeQuery(developerQuery, Person::class.java)?.executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager?.createNativeQuery("delete from `person`;")
    }

    @Test
    fun saveDeveloper() {
        val developer =
            Developer(
                level = 3,
                name = Name(firstName = "Rosana", lastName = "Pires"),
                email = "rosana@email.com",
                age = 39
            )

        mockMvc.perform(
            post("/developers").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developer))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.age", `is`(39)))
            .andExpect(jsonPath("$.name.lastName", `is`("Pires")))
            .andExpect(jsonPath("$.email", `is`("rosana@email.com")))
            .andExpect(jsonPath("$.level", `is`(3)))
    }

    @Test
    fun findAllDevelopers() {
        mockMvc.perform(get("/developers").param("page", "0").param("max", "10"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id", `is`(-1)))
            .andExpect(jsonPath("$[0].age", `is`(29)))
            .andExpect(jsonPath("$[0].name.lastName", `is`("Costa")))
            .andExpect(jsonPath("$[0].email", `is`("josecc@email.com")))
            .andExpect(jsonPath("$[0].level", `is`(2)))
    }

    @Test
    fun findDeveloperById() {
        mockMvc.perform(get("/developers/{id}", -1))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(-1)))
            .andExpect(jsonPath("$.age", `is`(29)))
            .andExpect(jsonPath("$.name.lastName", `is`("Costa")))
            .andExpect(jsonPath("$.email", `is`("josecc@email.com")))
            .andExpect(jsonPath("$.level", `is`(2)))
    }

    @Test
    fun updateDeveloper() {
        val developer =
            Developer(
                level = 7,
                name = Name(firstName = "Pedro", lastName = "Santos"),
                email = "pedro@email.com",
                age = 31,
                id = -1
            )

        mockMvc.perform(
            put("/developers").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developer))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(-1)))
            .andExpect(jsonPath("$.age", `is`(31)))
            .andExpect(jsonPath("$.name.lastName", `is`("Santos")))
            .andExpect(jsonPath("$.email", `is`("pedro@email.com")))
            .andExpect(jsonPath("$.level", `is`(7)))
    }

    @Test
    fun deleteDeveloper() {
        mockMvc.perform(delete("/developers/{id}", -1).with(csrf()))
            .andExpect(status().isNoContent)

        val developers: MutableList<Developer>? = entityManager
            ?.createQuery("from Developer d order by id", Developer::class.java)
            ?.resultList

        assertEquals(0, developers?.size)
    }
}