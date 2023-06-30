package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.Mentoring
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
class MentoringServiceImplTests(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val mentoringService: MentoringService
) {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @BeforeEach
    fun setUp() {
        val mentoringQuery: String =
            "insert into " +
                    "`activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)" +
                    " values ('mentoring', -1, 'Orientação a objetos', 'Orientação a objetos com Kotlin', NULL, NULL);"

        entityManager.createNativeQuery(mentoringQuery, Mentoring::class.java).executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager.createNativeQuery("delete from `activity`;")
    }

    @Test
    fun saveMentoring() {
        var mentorings: List<Mentoring> = entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .resultList

        assertEquals(1, mentorings.size)
        assertEquals("Orientação a objetos (mentoring)", mentorings[0].toString())
        assertEquals(-1, mentorings[0].id)

        val mentoring = Mentoring(date = null, hours = null, description = "Agile", details = "Gestão de times ágeis")

        mentoringService.saveMentoring(mentoring)

        mentorings = entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .resultList

        assertEquals(2, mentorings.size)
        assertEquals("Agile (mentoring)", mentorings[1].toString())
    }

    @Test
    fun findAllMentoring() {
        val mentorings: List<Mentoring> = mentoringService.findAllMentoring(page, max)

        assertEquals(1, mentorings.size)
        assertEquals("Orientação a objetos (mentoring)", mentorings[0].toString())
        assertEquals(-1, mentorings[0].id)
    }

    @Test
    fun findMentoringById() {
        val mentoring: Mentoring = mentoringService.findMentoringById(-1)

        assertEquals("Orientação a objetos (mentoring)", mentoring.toString())
        assertThrows<NoResultException> { mentoringService.findMentoringById(0) }
        assertThrows<NumberFormatException> { mentoringService.findMentoringById("aaa".toInt()) }
    }

    @Test
    fun updateMentoring() {
        val mentoring =
            Mentoring(date = null, hours = null, description = "Agile", details = "Gestão de times ágeis", id = -1)

        mentoringService.updateMentoring(mentoring)

        val mentorings: List<Mentoring> = entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .resultList

        assertEquals(1, mentorings.size)
        assertEquals("Agile (mentoring)", mentorings[0].toString())
        assertEquals(-1, mentorings[0].id)
    }

    @Test
    fun deleteMentoring() {
        mentoringService.deleteMentoring(-1)

        val mentorings: List<Mentoring> = entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .resultList

        assertEquals(0, mentorings.size)
    }
}