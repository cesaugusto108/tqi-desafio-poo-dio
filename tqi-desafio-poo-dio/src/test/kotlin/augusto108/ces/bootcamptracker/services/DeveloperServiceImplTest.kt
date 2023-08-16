package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.model.entities.Name
import jakarta.persistence.NoResultException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class DeveloperServiceImplTest(@Autowired private val developerService: DeveloperService) : TestContainersConfig() {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @Test
    @Order(3)
    fun saveDeveloper() {
        var developers: List<DeveloperDTO> = developerService.findAllDevelopers(page, max)

        assertEquals(2, developers.size)
        assertEquals("(4) Carlos Antônio Moura (carlos@email.com)", developers[0].toString())
        assertEquals(-2, developers[0].id)

        val developer = Developer(
            name = Name("Daniela", "Pereira", "Melo"),
            email = "daniela@email.com",
            age = 38,
            level = 4
        )

        developerService.saveDeveloper(developer)

        developers = developerService.findAllDevelopers(page, max)

        assertEquals(3, developers.size)
        assertEquals("(4) Daniela Pereira Melo (daniela@email.com)", developers[2].toString())

        developerService.deleteDeveloper(developers[2].id)
    }

    @Test
    @Order(1)
    fun findAllDevelopers() {
        val developers: List<DeveloperDTO> = developerService.findAllDevelopers(page, max)

        assertEquals(2, developers.size)
        assertEquals("(4) Carlos Antônio Moura (carlos@email.com)", developers[0].toString())
        assertEquals(-2, developers[0].id)
    }

    @Test
    @Order(2)
    fun findDeveloperById() {
        val developer: DeveloperDTO = developerService.findDeveloperById(-2)

        assertEquals("(4) Carlos Antônio Moura (carlos@email.com)", developer.toString())
        assertThrows<NoResultException> { developerService.findDeveloperById(0) }
        assertThrows<NumberFormatException> { developerService.findDeveloperById("aaa".toInt()) }
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
            id = -2,
            level = 6
        )

        developerService.updateDeveloper(developer)

        val developers: List<DeveloperDTO> = developerService.findAllDevelopers(page, max)

        assertEquals(2, developers.size)
        assertEquals("(6) Paula Campos Resende (paula@email.com)", developers[0].toString())
        assertEquals("pcresende", developers[0].username)
        assertEquals(-2, developers[0].id)
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

        developerService.saveDeveloper(developer)

        var developers: List<DeveloperDTO> = developerService.findAllDevelopers(page, max)

        developerService.deleteDeveloper(developers[2].id)

        developers = developerService.findAllDevelopers(page, max)

        assertEquals(2, developers.size)
    }

    @Test
    @Order(6)
    fun activateDeveloper() {
        val developer: DeveloperDTO = developerService.findDeveloperById(-1)

        developerService.activateDeveloper(developer.id)

        val activeDeveloper: DeveloperDTO = developerService.findDeveloperById(developer.id)

        assertTrue(activeDeveloper.active)
    }

    @Test
    @Order(7)
    fun deactivateDeveloper() {
        val developer: DeveloperDTO = developerService.findDeveloperById(-1)

        developerService.deactivateDeveloper(developer.id)

        val inactiveDeveloper: DeveloperDTO = developerService.findDeveloperById(developer.id)

        assertTrue(!inactiveDeveloper.active)
    }
}