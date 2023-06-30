package augusto108.ces.bootcamptracker.services

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
class CourseServiceImplTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val courseService: CourseService
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

        courseService.saveCourse(course)

        courses = entityManager
            .createQuery("from Course order by id", Course::class.java)
            .resultList

        assertEquals(2, courses.size)
        assertEquals("MySQL (course)", courses[1].toString())
    }

    @Test
    fun findAllCourses() {
        val courses: List<Course> = courseService.findAllCourses(0, 10)

        assertEquals(1, courses.size)
        assertEquals("Sintaxe Java (course)", courses[0].toString())
        assertEquals(-2, courses[0].id)
    }

    @Test
    fun findCourseById() {
        val course: Course = courseService.findCourseById(-2)

        assertEquals("Sintaxe Java (course)", course.toString())
    }

    @Test
    fun updateCourse() {
        val course =
            Course(date = null, hours = 300, description = "MySQL", details = "Banco de dados relacional MySQL", -2)

        courseService.updateCourse(course)

        val courses: List<Course> = entityManager
            .createQuery("from Course order by id", Course::class.java)
            .resultList

        assertEquals(1, courses.size)
        assertEquals("MySQL (course)", courses[0].toString())
        assertEquals(-2, courses[0].id)
    }

    @Test
    fun deleteCourse() {
        courseService.deleteCourse(-2)

        val courses: List<Course> = entityManager
            .createQuery("from Course order by id", Course::class.java)
            .resultList

        assertEquals(0, courses.size)
    }
}