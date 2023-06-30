package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.Instructor
import augusto108.ces.bootcamptracker.model.Name
import augusto108.ces.bootcamptracker.model.Person
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
class InstructorServiceImplTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val instructorService: InstructorService
) {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @BeforeEach
    fun setUp() {
        val instructorQuery: String =
            "insert into " +
                    "`person` (`person_type`, `id`, `person_age`, `email`, `first_name`, `last_name`, `middle_name`, `developer_level`)" +
                    " values ('instructor', -2, 32, 'maria@email.com', 'Maria', 'Souza', '', NULL);"

        entityManager.createNativeQuery(instructorQuery, Person::class.java).executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager.createNativeQuery("delete from `person`;")
    }

    @Test
    fun saveInstructor() {
        var instructors: List<Instructor> = entityManager
            .createQuery("from Instructor order by id", Instructor::class.java)
            .resultList

        assertEquals(1, instructors.size)
        assertEquals("Maria Souza (maria@email.com)", instructors[0].toString())
        assertEquals(-2, instructors[0].id)

        val instructor = Instructor(name = Name("João", "Roberto", "Silva"), email = "joao@email.com", age = 37)

        instructorService.saveInstructor(instructor)

        instructors = entityManager
            .createQuery("from Instructor order by id", Instructor::class.java)
            .resultList

        assertEquals(2, instructors.size)
        assertEquals("João Roberto Silva (joao@email.com)", instructors[1].toString())
    }

    @Test
    fun findAllInstructors() {
        val instructors: List<Instructor> = instructorService.findAllInstructors(page, max)

        assertEquals(1, instructors.size)
        assertEquals("Maria Souza (maria@email.com)", instructors[0].toString())
        assertEquals(-2, instructors[0].id)
    }

    @Test
    fun findInstructorById() {
        val instructor: Instructor = instructorService.findInstructorById(-2)

        assertEquals("Maria Souza (maria@email.com)", instructor.toString())
        assertThrows<NoResultException> { instructorService.findInstructorById(0) }
        assertThrows<NumberFormatException> { instructorService.findInstructorById("aaa".toInt()) }
    }

    @Test
    fun updateInstructor() {
        val instructor = Instructor(
            name = Name(firstName = "Josias", middleName = "Campos", lastName = "Souza"),
            email = "josias@email.com",
            age = 40,
            id = -2
        )

        instructorService.updateInstructor(instructor)

        val instructors: List<Instructor> = entityManager
            .createQuery("from Instructor order by id", Instructor::class.java)
            .resultList

        assertEquals(1, instructors.size)
        assertEquals("Josias Campos Souza (josias@email.com)", instructors[0].toString())
        assertEquals(-2, instructors[0].id)
    }

    @Test
    fun deleteInstructor() {
        instructorService.deleteInstructor(-2)

        val instructors: List<Instructor> = entityManager
            .createQuery("from Instructor order by id", Instructor::class.java)
            .resultList

        assertEquals(0, instructors.size)
    }
}

