package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.entities.Developer
import augusto108.ces.bootcamptracker.entities.Name
import augusto108.ces.bootcamptracker.services.DeveloperService
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
class DeveloperControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper,
    @Autowired private val developerService: DeveloperService
) : TestContainersConfig() {
    @Value("\${page.value}")
    var page: String = ""

    @Value("\${max.value}")
    var max: String = ""

    @Test
    @Order(4)
    fun saveDeveloper() {
        val developer =
            Developer(
                level = 3,
                name = Name(firstName = "Rosana", lastName = "Pires"),
                email = "rosana@email.com",
                age = 39
            )

        mockMvc.perform(
            post("${API_VERSION}developers").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developer))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.age", `is`(39)))
            .andExpect(jsonPath("$.name.lastName", `is`("Pires")))
            .andExpect(jsonPath("$.email", `is`("rosana@email.com")))
            .andExpect(jsonPath("$.level", `is`(3)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}developers/1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}developers")))

        val developers: List<DeveloperDTO> =
            developerService.findAllDevelopers(Integer.parseInt(page), Integer.parseInt(max))
        developerService.deleteDeveloper(developers[1].id)
    }

    @Test
    @Order(1)
    fun findAllDevelopers() {
        mockMvc.perform(get("${API_VERSION}developers").param("page", "0").param("max", "10"))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id", `is`(-2)))
            .andExpect(jsonPath("$[0].age", `is`(28)))
            .andExpect(jsonPath("$[0].name.lastName", `is`("Moura")))
            .andExpect(jsonPath("$[0].email", `is`("carlos@email.com")))
            .andExpect(jsonPath("$[0].level", `is`(4)))
            .andExpect(jsonPath("$[0].links[0].href", `is`("http://localhost${API_VERSION}developers")))
            .andExpect(jsonPath("$[0].links[1].href", `is`("http://localhost${API_VERSION}developers/-2")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}developers")
                .param("page", "0")
                .param("max", "10")
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "email: \"josecc@email.com\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(2)
    fun findDeveloperById() {
        mockMvc.perform(get("${API_VERSION}developers/{id}", -1))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(-1)))
            .andExpect(jsonPath("$.age", `is`(29)))
            .andExpect(jsonPath("$.name.lastName", `is`("Costa")))
            .andExpect(jsonPath("$.email", `is`("josecc@email.com")))
            .andExpect(jsonPath("$.level", `is`(2)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}developers/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}developers")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}developers/{id}", -1)
                .accept(UtilMediaType.APPLICATION_YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.APPLICATION_YAML))
            .andReturn()

        val yamlResponse: String = "email: \"josecc@email.com\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(3)
    fun updateDeveloper() {
        val developer =
            Developer(
                level = 7,
                name = Name(firstName = "Pedro", lastName = "Santos"),
                email = "pedro@email.com",
                username = "pedrosantos",
                age = 31,
                id = -1
            )

        mockMvc.perform(
            put("${API_VERSION}developers").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developer))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`(-1)))
            .andExpect(jsonPath("$.age", `is`(31)))
            .andExpect(jsonPath("$.name.lastName", `is`("Santos")))
            .andExpect(jsonPath("$.email", `is`("pedro@email.com")))
            .andExpect(jsonPath("$.username", `is`("pedrosantos")))
            .andExpect(jsonPath("$.level", `is`(7)))
            .andExpect(jsonPath("$._links.self.href", `is`("http://localhost${API_VERSION}developers/-1")))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}developers")))
    }

    @Test
    @Order(5)
    fun deleteDeveloper() {
        val developer =
            Developer(
                level = 7,
                name = Name(firstName = "Joaquim", lastName = "Nunes"),
                email = "joaquim@email.com",
                username = "jnunes",
                age = 32
            )

        val d: DeveloperDTO = developerService.saveDeveloper(developer)

        mockMvc.perform(delete("${API_VERSION}developers/{id}", d.id).with(csrf()))
            .andExpect(status().isNoContent)

        val developers: List<DeveloperDTO> =
            developerService.findAllDevelopers(Integer.parseInt(page), Integer.parseInt(max))

        assertEquals(2, developers.size)
    }
}