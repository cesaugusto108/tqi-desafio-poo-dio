package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.entities.Developer
import augusto108.ces.bootcamptracker.entities.Name
import augusto108.ces.bootcamptracker.entities.Person
import jakarta.persistence.EntityManager
import jakarta.persistence.NoResultException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
class DeveloperServiceImplTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val developerService: DeveloperService
) {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @BeforeEach
    fun setUp() {
        val developerQuery: String =
            "insert into " +
                    "`person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `password`, `username`, `developer_level`)" +
                    " values ('developer', -1, 29, 'josecc@email.com', 'José', 'Costa', 'Carlos', 'josecc', '1234', 2);"

        entityManager.createNativeQuery(developerQuery, Person::class.java).executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager.createNativeQuery("delete from `person`;")
    }

    @Test
    fun saveDeveloper() {
        var developers: List<Developer> = entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .resultList

        assertEquals(1, developers.size)
        assertEquals("(2) José Carlos Costa (josecc@email.com)", developers[0].toString())
        assertEquals(-1, developers[0].id)

        val developer =
            Developer(name = Name("Daniela", "Pereira", "Melo"), email = "daniela@email.com", age = 38, level = 4)

        developerService.saveDeveloper(developer)

        developers = entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .resultList

        assertEquals(2, developers.size)
        assertEquals("(4) Daniela Pereira Melo (daniela@email.com)", developers[1].toString())
    }

    @Test
    fun findAllDevelopers() {
        val developers: List<DeveloperDTO> = developerService.findAllDevelopers(page, max)

        assertEquals(1, developers.size)
        assertEquals("(2) José Carlos Costa (josecc@email.com)", developers[0].toString())
        assertEquals(-1, developers[0].id)
    }

    @Test
    fun findDeveloperById() {
        val developer: DeveloperDTO = developerService.findDeveloperById(-1)

        assertEquals("(2) José Carlos Costa (josecc@email.com)", developer.toString())
        assertThrows<NoResultException> { developerService.findDeveloperById(0) }
        assertThrows<NumberFormatException> { developerService.findDeveloperById("aaa".toInt()) }
    }

    @Test
    fun updateDeveloper() {
        val developer = Developer(
            name = Name(firstName = "Paula", middleName = "Campos", lastName = "Resende"),
            email = "paula@email.com",
            username = "pcresende",
            password = "1234",
            age = 40,
            id = -1,
            level = 6
        )

        developerService.updateDeveloper(developer)

        val developers: List<Developer> = entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .resultList

        assertEquals(1, developers.size)
        assertEquals("(6) Paula Campos Resende (paula@email.com)", developers[0].toString())
        assertEquals("pcresende", developers[0].username)
        assertEquals(-1, developers[0].id)
    }

    @Test
    fun deleteDeveloper() {
        developerService.deleteDeveloper(-1)

        val developers: List<Developer> = entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .resultList

        assertEquals(0, developers.size)
    }
}