package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.model.datatypes.Name
import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
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
import java.util.*

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class InstructorServiceImplTest @Autowired constructor(private val instructorService: InstructorService) :
    TestContainersConfig() {

    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @Test
    @Order(3)
    fun saveInstructor() {
        var instructors: List<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max).toList()
        assertEquals(3, instructors.size)
        assertEquals("Osvaldo Pires (osvaldo@email.com)", instructors[0].content.toString())
        assertEquals(UUID.fromString("4879ee9d-27d3-4b4b-a39c-1360d70d5a00"), instructors[0].content?.id)
        val instructor = Instructor(name = Name("João", "Roberto", "Silva"), email = "joao@email.com", age = 37)
        instructorService.saveInstructor(instructor)

        instructors = instructorService.findAllInstructors(page, max).toList()
        val email = "joao@email.com"
        val persistedInstructor: InstructorDTO? = instructors
            .stream()
            .filter { it.content?.email == email }.findFirst().get().content

        assertEquals(4, instructors.size)
        assertEquals("João Roberto Silva (joao@email.com)", persistedInstructor.toString())
        instructorService.deleteInstructor(persistedInstructor?.id.toString())
    }

    @Test
    @Order(1)
    fun findAllInstructors() {
        val instructors: List<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max).toList()
        assertEquals(3, instructors.size)
        assertEquals("Osvaldo Pires (osvaldo@email.com)", instructors[0].content.toString())
        assertEquals(UUID.fromString("4879ee9d-27d3-4b4b-a39c-1360d70d5a00"), instructors[0].content?.id)
    }

    @Test
    @Order(2)
    fun findInstructorById() {
        val instructor: InstructorDTO = instructorService.findInstructorById("4879ee9d-27d3-4b4b-a39c-1360d70d5a04")
        assertEquals("Maria Souza (maria@email.com)", instructor.toString())
        assertThrows<NoResultException> { instructorService.findInstructorById("4879ee9d-27d3-4b4b-a39c-1360d70d5abb") }
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
            id = UUID.fromString("4879ee9d-27d3-4b4b-a39c-1360d70d5a04")
        )

        instructorService.updateInstructor(instructor)
        val instructors: List<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max).toList()
        assertEquals(3, instructors.size)
        assertEquals("Josias Campos Souza (josias@email.com)", instructors[1].content.toString())
        assertEquals("jcsouza", instructors[1].content?.username)
        assertEquals(UUID.fromString("4879ee9d-27d3-4b4b-a39c-1360d70d5a04"), instructors[1].content?.id)
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

        val persistedInstructor: InstructorDTO = instructorService.saveInstructor(instructor)
        instructorService.deleteInstructor(persistedInstructor.id.toString())
        val instructors: List<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max).toList()
        assertEquals(3, instructors.size)
    }

    @Test
    @Order(6)
    fun activateInstructor() {
        val instructor: InstructorDTO = instructorService.findInstructorById("4879ee9d-27d3-4b4b-a39c-1360d70d5a04")
        instructorService.activateInstructor(instructor.id.toString())
        val activeInstructor: InstructorDTO = instructorService.findInstructorById(instructor.id.toString())
        assertTrue(activeInstructor.active)
    }

    @Test
    @Order(7)
    fun deactivateInstructor() {
        val instructor: InstructorDTO = instructorService.findInstructorById("e8fd1a04-1c85-45e0-8f35-8ee8520e1800")
        instructorService.deactivateInstructor(instructor.id.toString())
        val inactiveInstructor: InstructorDTO = instructorService.findInstructorById(instructor.id.toString())
        assertTrue(!inactiveInstructor.active)
    }
}

