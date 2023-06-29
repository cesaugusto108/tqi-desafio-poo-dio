package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Mentoring
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.*

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
class MentoringDaoImplTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val mentoringDao: MentoringDao
) {
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

        mentoringDao.saveMentoring(mentoring)

        mentorings = entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .resultList

        assertEquals(2, mentorings.size)
        assertEquals("Agile (mentoring)", mentorings[1].toString())
    }

    @Test
    fun findAllMentoring() {
        val mentorings: List<Mentoring> = mentoringDao.findAllMentoring()

        assertEquals(1, mentorings.size)
        assertEquals("Orientação a objetos (mentoring)", mentorings[0].toString())
        assertEquals(-1, mentorings[0].id)
    }

    @Test
    fun findMentoringById() {
        val mentoring: Mentoring = mentoringDao.findMentoringById(-1)

        assertEquals("Orientação a objetos (mentoring)", mentoring.toString())
    }

    @Test
    fun updateMentoring() {
        val mentoring =
            Mentoring(date = null, hours = null, description = "Agile", details = "Gestão de times ágeis", id = -1)

        mentoringDao.updateMentoring(mentoring)

        val mentorings: List<Mentoring> = entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .resultList

        assertEquals(1, mentorings.size)
        assertEquals("Agile (mentoring)", mentorings[0].toString())
        assertEquals(-1, mentorings[0].id)
    }

    @Test
    fun deleteMentoring() {
        mentoringDao.deleteMentoring(-1)

        val mentorings: List<Mentoring> = entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .resultList

        assertEquals(0, mentorings.size)
    }
}