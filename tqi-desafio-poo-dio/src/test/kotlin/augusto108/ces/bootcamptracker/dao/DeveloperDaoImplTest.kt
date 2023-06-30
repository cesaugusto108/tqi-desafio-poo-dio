package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Developer
import augusto108.ces.bootcamptracker.model.Name
import augusto108.ces.bootcamptracker.model.Person
import jakarta.persistence.EntityManager
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
class DeveloperDaoImplTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val developerDao: DeveloperDao
) {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @BeforeEach
    fun setUp() {
        val developerQuery: String =
            "insert into " +
                    "`person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `developer_level`)" +
                    " values ('developer', -1, 29, 'josecc@email.com', 'José', 'Costa', 'Carlos', 2);"

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

        developerDao.saveDeveloper(developer)

        developers = entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .resultList

        assertEquals(2, developers.size)
        assertEquals("(4) Daniela Pereira Melo (daniela@email.com)", developers[1].toString())
    }

    @Test
    fun findAllDevelopers() {
        val developers: List<Developer> = developerDao.findAllDevelopers(page, max)

        assertEquals(1, developers.size)
        assertEquals("(2) José Carlos Costa (josecc@email.com)", developers[0].toString())
        assertEquals(-1, developers[0].id)
    }

    @Test
    fun findDeveloperById() {
        val developer: Developer = developerDao.findDeveloperById(-1)

        assertEquals("(2) José Carlos Costa (josecc@email.com)", developer.toString())
    }

    @Test
    fun updateDeveloper() {
        val developer = Developer(
            name = Name(firstName = "Paula", middleName = "Campos", lastName = "Resende"),
            email = "paula@email.com",
            age = 40,
            id = -1,
            level = 6
        )

        developerDao.updateDeveloper(developer)

        val developers: List<Developer> = entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .resultList

        assertEquals(1, developers.size)
        assertEquals("(6) Paula Campos Resende (paula@email.com)", developers[0].toString())
        assertEquals(-1, developers[0].id)
    }

    @Test
    fun deleteDeveloper() {
        developerDao.deleteDeveloper(-1)

        val developers: List<Developer> = entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .resultList

        assertEquals(0, developers.size)
    }
}