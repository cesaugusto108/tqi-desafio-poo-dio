package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Course
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
class CourseDaoImplTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val courseDao: CourseDao
) {
    @BeforeEach
    fun setUp() {
        val courseQuery: String =
            "insert into " +
                    "`activity` (`activity_type`, `id`, `activity_description`, `activity_details`, `mentoring_date`, `course_hours`)" +
                    " values ('course', -2, 'Sintaxe Java', 'Aprendendo a sintaxe Java', NULL, 300);"

        entityManager.createNativeQuery(courseQuery, Course::class.java).executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager.createNativeQuery("delete from `activity`;")
    }

    @Test
    fun saveCourse() {
        var courses: List<Course> = entityManager
            .createQuery("from Course order by id", Course::class.java)
            .resultList

        assertEquals(1, courses.size)
        assertEquals("Sintaxe Java (course)", courses[0].toString())
        assertEquals(-2, courses[0].id)

        val course =
            Course(date = null, hours = 300, description = "MySQL", details = "Banco de dados relacional MySQL")

        courseDao.saveCourse(course)

        courses = entityManager
            .createQuery("from Course order by id", Course::class.java)
            .resultList

        assertEquals(2, courses.size)
        assertEquals("MySQL (course)", courses[1].toString())
    }

    @Test
    fun findAllCourses() {
        val courses: List<Course> = courseDao.findAllCourses()

        assertEquals(1, courses.size)
        assertEquals("Sintaxe Java (course)", courses[0].toString())
        assertEquals(-2, courses[0].id)
    }

    @Test
    fun findCourseById() {
        val course: Course = courseDao.findCourseById(-2)

        assertEquals("Sintaxe Java (course)", course.toString())
    }

    @Test
    fun updateCourse() {
        val course =
            Course(date = null, hours = 300, description = "MySQL", details = "Banco de dados relacional MySQL", -2)

        courseDao.updateCourse(course)

        val courses: List<Course> = entityManager
            .createQuery("from Course order by id", Course::class.java)
            .resultList

        assertEquals(1, courses.size)
        assertEquals("MySQL (course)", courses[0].toString())
        assertEquals(-2, courses[0].id)
    }

    @Test
    fun deleteCourse() {
        courseDao.deleteCourse(-2)

        val courses: List<Course> = entityManager
            .createQuery("from Course order by id", Course::class.java)
            .resultList

        assertEquals(0, courses.size)
    }
}