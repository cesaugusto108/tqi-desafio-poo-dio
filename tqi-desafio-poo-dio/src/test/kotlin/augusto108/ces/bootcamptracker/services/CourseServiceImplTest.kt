package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.dto.CourseDTO
import augusto108.ces.bootcamptracker.entities.Course
import jakarta.persistence.NoResultException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
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
class CourseServiceImplTest(@Autowired private val courseService: CourseService) : TestContainersConfig() {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @Test
    @Order(3)
    fun saveCourse() {
        var courses: List<CourseDTO> = courseService.findAllCourses(page, max)

        assertEquals(2, courses.size)
        assertEquals("Sintaxe Kotlin (course)", courses[0].toString())
        assertEquals(-4, courses[0].id)

        val course =
            Course(date = null, hours = 300, description = "Django", details = "Python and Django")

        courseService.saveCourse(course)

        courses = courseService.findAllCourses(page, max)

        assertEquals(3, courses.size)
        assertEquals("Django (course)", courses[2].toString())

        courseService.deleteCourse(courses[2].id)
    }

    @Test
    @Order(1)
    fun findAllCourses() {
        val courses: List<CourseDTO> = courseService.findAllCourses(page, max)

        assertEquals(2, courses.size)
        assertEquals("Sintaxe Kotlin (course)", courses[0].toString())
        assertEquals(-4, courses[0].id)
    }

    @Test
    @Order(2)
    fun findCourseById() {
        val course: CourseDTO = courseService.findCourseById(-4)

        assertEquals("Sintaxe Kotlin (course)", course.toString())
        assertThrows<NoResultException> { courseService.findCourseById(0) }
        assertThrows<NumberFormatException> { courseService.findCourseById("aaa".toInt()) }
    }

    @Test
    @Order(4)
    fun updateCourse() {
        val course = Course(date = null, hours = 300, description = "Scrum", details = "Scrum and Agile", -3)

        courseService.updateCourse(course)

        val courses: List<CourseDTO> = courseService.findAllCourses(page, max)

        assertEquals(2, courses.size)
        assertEquals("Scrum (course)", courses[1].toString())
        assertEquals(-3, courses[1].id)
    }

    @Test
    @Order(5)
    fun deleteCourse() {
        val course = Course(date = null, hours = 300, description = "POO", details = "Programação orientada a objetos")

        courseService.saveCourse(course)

        var courses: List<CourseDTO> = courseService.findAllCourses(page, max)

        courseService.deleteCourse(courses[2].id)

        courses = courseService.findAllCourses(page, max)

        assertEquals(2, courses.size)
    }
}