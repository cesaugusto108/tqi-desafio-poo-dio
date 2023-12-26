package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.config.web.HEADER_KEY
import augusto108.ces.bootcamptracker.config.web.HEADER_VALUE
import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.model.entities.Name
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

        val result: MvcResult = mockMvc.perform(
            post("${API_VERSION}developers")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developer))
        )
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.age", `is`(39)))
            .andExpect(jsonPath("$.name.lastName", `is`("Pires")))
            .andExpect(jsonPath("$.email", `is`("rosana@email.com")))
            .andExpect(jsonPath("$.level", `is`(3)))
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}developers")))
            .andReturn()

        val locationHeader: String? = result.response.getHeader("Location")
        val jsonResult: String = result.response.contentAsString
        val savedDeveloper: Developer = objectMapper.readerFor(Developer::class.java).readValue(jsonResult)
        val uri = "http://localhost${API_VERSION}developers/${savedDeveloper.id}"
        assertEquals(locationHeader, uri)

        val pageInt: Int = Integer.parseInt(page)
        val maxInt: Int = Integer.parseInt(max)
        var pagedModel: PagedModel<EntityModel<DeveloperDTO>> = developerService.findAllDevelopers(pageInt, maxInt)
        assertEquals(4, pagedModel.content.size)
        val persistedDeveloper: DeveloperDTO? = pagedModel.content
            .stream()
            .filter { it.content?.email == "rosana@email.com" }
            .findFirst().get().content
        developerService.deleteDeveloper(persistedDeveloper?.id.toString())
        pagedModel = developerService.findAllDevelopers(pageInt, maxInt)
        assertEquals(3, pagedModel.content.size)
    }

    @Test
    @Order(1)
    fun findAllDevelopers() {
        mockMvc.perform(
            get("${API_VERSION}developers")
                .param("page", "0")
                .param("max", "10")
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(header().string("X-Page-Number", "0"))
            .andExpect(header().string("X-Page-Size", "10"))
            .andExpect(jsonPath("$._embedded.developerDTOList[0].id", `is`("96f2c93e-dc1b-4ce1-9aee-989a2cd9f722")))
            .andExpect(jsonPath("$._embedded.developerDTOList[0].age", `is`(49)))
            .andExpect(jsonPath("$._embedded.developerDTOList[0].name.lastName", `is`("Alves")))
            .andExpect(jsonPath("$._embedded.developerDTOList[0].email", `is`("fernando@email.com")))
            .andExpect(jsonPath("$._embedded.developerDTOList[0].level", `is`(9)))
            .andExpect(
                jsonPath(
                    "$._embedded.developerDTOList[0]._links.self.href",
                    `is`("http://localhost${API_VERSION}developers")
                )
            )
            .andExpect(
                jsonPath(
                    "$._embedded.developerDTOList[0]._links.developer96f2c93e-dc1b-4ce1-9aee-989a2cd9f722.href",
                    `is`("http://localhost${API_VERSION}developers/96f2c93e-dc1b-4ce1-9aee-989a2cd9f722")
                )
            )
            .andExpect(jsonPath("$.page.size", `is`(10)))
            .andExpect(jsonPath("$.page.totalElements", `is`(10)))
            .andExpect(jsonPath("$.page.totalPages", `is`(1)))
            .andExpect(jsonPath("$.page.number", `is`(0)))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}developers")
                .param("page", "0")
                .param("max", "10")
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.YAML))
            .andReturn()

        val yamlResponse = "email: \"fernando@email.com\""

        assertTrue(result.response.contentAsString.contains(yamlResponse))
    }

    @Test
    @Order(2)
    fun findDeveloperById() {
        mockMvc.perform(
            get("${API_VERSION}developers/{id}", "96f2c93e-dc1b-4ce1-9aee-989a2cd9f7ad").header(
                HEADER_KEY,
                HEADER_VALUE
            )
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`("96f2c93e-dc1b-4ce1-9aee-989a2cd9f7ad")))
            .andExpect(jsonPath("$.age", `is`(29)))
            .andExpect(jsonPath("$.name.lastName", `is`("Costa")))
            .andExpect(jsonPath("$.email", `is`("josecc@email.com")))
            .andExpect(jsonPath("$.level", `is`(2)))
            .andExpect(
                jsonPath(
                    "$._links.self.href",
                    `is`("http://localhost${API_VERSION}developers/96f2c93e-dc1b-4ce1-9aee-989a2cd9f7ad")
                )
            )
            .andExpect(jsonPath("$._links.all.href", `is`("http://localhost${API_VERSION}developers")))

        val result: MvcResult = mockMvc.perform(
            get("${API_VERSION}developers/{id}", "96f2c93e-dc1b-4ce1-9aee-989a2cd9f7ad")
                .header(HEADER_KEY, HEADER_VALUE)
                .accept(UtilMediaType.YAML)
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(UtilMediaType.YAML))
            .andReturn()

        val yamlResponse = "email: \"josecc@email.com\""

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
                id = UUID.fromString("d8b5e8de-d938-4daa-9699-b9cfcc599e37")
            )

        mockMvc.perform(
            put("${API_VERSION}developers")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(developer))
        )
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id", `is`("d8b5e8de-d938-4daa-9699-b9cfcc599e37")))
            .andExpect(jsonPath("$.age", `is`(31)))
            .andExpect(jsonPath("$.name.lastName", `is`("Santos")))
            .andExpect(jsonPath("$.email", `is`("pedro@email.com")))
            .andExpect(jsonPath("$.username", `is`("pedrosantos")))
            .andExpect(jsonPath("$.level", `is`(7)))
            .andExpect(
                jsonPath(
                    "$._links.self.href",
                    `is`("http://localhost${API_VERSION}developers/d8b5e8de-d938-4daa-9699-b9cfcc599e37")
                )
            )
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

        mockMvc.perform(
            delete("${API_VERSION}developers/{id}", d.id.toString())
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val pagedModel: PagedModel<EntityModel<DeveloperDTO>> =
            developerService.findAllDevelopers(Integer.parseInt(page), Integer.parseInt(max))

        assertEquals(3, pagedModel.content.size)
    }

    @Test
    @Order(6)
    fun activateDeveloper() {
        mockMvc.perform(
            patch("${API_VERSION}developers/active/{id}", "96f2c93e-dc1b-4ce1-9aee-989a2cd9f7ad")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val developer: DeveloperDTO = developerService.findDeveloperById("96f2c93e-dc1b-4ce1-9aee-989a2cd9f7ad")

        assertTrue(developer.active)
    }

    @Test
    @Order(7)
    fun deactivateDeveloper() {
        mockMvc.perform(
            patch("${API_VERSION}developers/inactive/{id}", "d8b5e8de-d938-4daa-9699-b9cfcc599e37")
                .with(csrf())
                .header(HEADER_KEY, HEADER_VALUE)
        )
            .andExpect(status().isNoContent)

        val developer: DeveloperDTO = developerService.findDeveloperById("d8b5e8de-d938-4daa-9699-b9cfcc599e37")

        assertTrue(!developer.active)
    }
}