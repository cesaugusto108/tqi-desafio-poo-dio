package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.model.datatypes.Name
import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import jakarta.persistence.NoResultException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.EntityModel
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DeveloperServiceImplTest @Autowired constructor(private val developerService: DeveloperService) :
    TestContainersConfig() {

    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @Test
    @Order(3)
    fun saveDeveloper() {
        var developers: List<EntityModel<DeveloperDTO>> = developerService.findAllDevelopers(page, max).toList()
        assertEquals(3, developers.size)
        assertEquals("(9) Fernando Alves (fernando@email.com)", developers[0].content.toString())
        assertEquals(UUID.fromString("96f2c93e-dc1b-4ce1-9aee-989a2cd9f722"), developers[0].content?.id)

        val developer = Developer(
            name = Name("Daniela", "Pereira", "Melo"),
            email = "daniela@email.com",
            age = 38,
            level = 4
        )

        developerService.saveDeveloper(developer)
        developers = developerService.findAllDevelopers(page, max).toList()
        val email = "daniela@email.com"
        val persistedDeveloper: DeveloperDTO? = developers
            .stream()
            .filter { it.content?.email == email }.findFirst().get().content

        assertEquals(4, developers.size)
        assertEquals("(4) Daniela Pereira Melo (daniela@email.com)", persistedDeveloper.toString())
        developerService.deleteDeveloper(persistedDeveloper?.id.toString())
    }

    @Test
    @Order(1)
    fun findAllDevelopers() {
        val developers: List<EntityModel<DeveloperDTO>> = developerService.findAllDevelopers(page, max).toList()
        assertEquals(3, developers.size)
        assertEquals("(2) José Carlos Costa (josecc@email.com)", developers[1].content.toString())
        assertEquals(UUID.fromString("96f2c93e-dc1b-4ce1-9aee-989a2cd9f7ad"), developers[1].content?.id)
    }

    @Test
    @Order(2)
    fun findDeveloperById() {
        val developer: DeveloperDTO = developerService.findDeveloperById("96f2c93e-dc1b-4ce1-9aee-989a2cd9f722")
        assertEquals("(9) Fernando Alves (fernando@email.com)", developer.toString())
        assertThrows<NoResultException> { developerService.findDeveloperById("96f2c93e-dc1b-4ce1-9aee-989a2cd9f788") }
    }

    @Test
    @Order(4)
    fun updateDeveloper() {
        val developer = Developer(
            name = Name(firstName = "Paula", middleName = "Campos", lastName = "Resende"),
            email = "paula@email.com",
            username = "pcresende",
            password = "1234",
            age = 40,
            id = UUID.fromString("d8b5e8de-d938-4daa-9699-b9cfcc599e37"),
            level = 6
        )

        developerService.updateDeveloper(developer)
        val developers: List<EntityModel<DeveloperDTO>> = developerService.findAllDevelopers(page, max).toList()
        assertEquals(3, developers.size)
        assertEquals("(6) Paula Campos Resende (paula@email.com)", developers[2].content.toString())
        assertEquals("pcresende", developers[2].content?.username)
        assertEquals(UUID.fromString("d8b5e8de-d938-4daa-9699-b9cfcc599e37"), developers[2].content?.id)
    }

    @Test
    @Order(5)
    fun deleteDeveloper() {
        val developer = Developer(
            name = Name("Juliana", "Pereira", "Silva"),
            email = "juliana@email.com",
            age = 28,
            level = 5
        )

        val persistedDeveloper: DeveloperDTO = developerService.saveDeveloper(developer)
        developerService.deleteDeveloper(persistedDeveloper.id.toString())
        val developers: List<EntityModel<DeveloperDTO>> = developerService.findAllDevelopers(page, max).toList()
        assertEquals(3, developers.size)
    }

    @Test
    @Order(6)
    fun activateDeveloper() {
        val developer: DeveloperDTO = developerService.findDeveloperById("96f2c93e-dc1b-4ce1-9aee-989a2cd9f7ad")
        developerService.activateDeveloper(developer.id.toString())
        val activeDeveloper: DeveloperDTO = developerService.findDeveloperById(developer.id.toString())
        assertTrue(activeDeveloper.active)
    }

    @Test
    @Order(7)
    fun deactivateDeveloper() {
        val developer: DeveloperDTO = developerService.findDeveloperById("d8b5e8de-d938-4daa-9699-b9cfcc599e37")
        developerService.deactivateDeveloper(developer.id.toString())
        val inactiveDeveloper: DeveloperDTO = developerService.findDeveloperById(developer.id.toString())
        assertTrue(!inactiveDeveloper.active)
    }
}