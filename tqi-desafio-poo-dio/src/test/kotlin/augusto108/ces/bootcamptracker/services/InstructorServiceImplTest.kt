package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.model.entities.Name
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

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class InstructorServiceImplTest(@Autowired private val instructorService: InstructorService) : TestContainersConfig() {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @Test
    @Order(3)
    fun saveInstructor() {
        var instructors: List<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max).toList()

        assertEquals(2, instructors.size)
        assertEquals("Maria Souza (maria@email.com)", instructors[1].content.toString())
        assertEquals(-3, instructors[1].content?.id)

        val instructor = Instructor(name = Name("João", "Roberto", "Silva"), email = "joao@email.com", age = 37)

        instructorService.saveInstructor(instructor)

        instructors = instructorService.findAllInstructors(page, max).toList()

        assertEquals(3, instructors.size)
        assertEquals("João Roberto Silva (joao@email.com)", instructors[2].content.toString())

        instructorService.deleteInstructor(instructors[2].content?.id!!)
    }

    @Test
    @Order(1)
    fun findAllInstructors() {
        val instructors: List<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max).toList()

        assertEquals(2, instructors.size)
        assertEquals("Maria Souza (maria@email.com)", instructors[1].content.toString())
        assertEquals(-3, instructors[1].content?.id)
    }

    @Test
    @Order(2)
    fun findInstructorById() {
        val instructor: InstructorDTO = instructorService.findInstructorById(-3)

        assertEquals("Maria Souza (maria@email.com)", instructor.toString())
        assertThrows<NoResultException> { instructorService.findInstructorById(0) }
        assertThrows<NumberFormatException> { instructorService.findInstructorById("aaa".toInt()) }
    }

    @Test
    @Order(4)
    fun updateInstructor() {
        val instructor = Instructor(
            name = Name(firstName = "Josias", middleName = "Campos", lastName = "Souza"),
            email = "josias@email.com",
            username = "jcsouza",
            password = "0987",
            age = 40,
            id = -3
        )

        instructorService.updateInstructor(instructor)

        val instructors: List<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max).toList()

        assertEquals(2, instructors.size)
        assertEquals("Josias Campos Souza (josias@email.com)", instructors[1].content.toString())
        assertEquals("jcsouza", instructors[1].content?.username)
        assertEquals(-3, instructors[1].content?.id)
    }

    @Test
    @Order(5)
    fun deleteInstructor() {
        val instructor = Instructor(
            name = Name(firstName = "Iasmin", middleName = "Lima", lastName = "Santos"),
            email = "iasmin@email.com",
            username = "iasminls",
            password = "1234",
            age = 34
        )

        instructorService.saveInstructor(instructor)

        var instructors: List<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max).toList()

        instructorService.deleteInstructor(instructors[2].content?.id!!)

        instructors = instructorService.findAllInstructors(page, max).toList()

        assertEquals(2, instructors.size)
    }

    @Test
    @Order(6)
    fun activateInstructor() {
        val instructor: InstructorDTO = instructorService.findInstructorById(-3)

        instructorService.activateInstructor(instructor.id)

        val activeInstructor: InstructorDTO = instructorService.findInstructorById(instructor.id)

        assertTrue(activeInstructor.active)
    }

    @Test
    @Order(7)
    fun deactivateInstructor() {
        val instructor: InstructorDTO = instructorService.findInstructorById(-3)

        instructorService.deactivateInstructor(instructor.id)

        val inactiveInstructor: InstructorDTO = instructorService.findInstructorById(instructor.id)

        assertTrue(!inactiveInstructor.active)
    }
}

