package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.model.dto.BootcampDTO
import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import jakarta.persistence.NoResultException
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.hateoas.EntityModel
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test")
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class BootcampServiceImplTest(@Autowired private val bootcampService: BootcampService) : TestContainersConfig() {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @Test
    @Order(3)
    fun saveBootcamp() {
        var bootcamps: List<EntityModel<BootcampDTO>> = bootcampService.findAllBootcamps(page, max).toList()

        assertEquals(3, bootcamps.size)
        assertEquals("AWS Experience", bootcamps[0].content.toString())
        assertEquals(-3, bootcamps[0].content?.id)

        val bootcamp = Bootcamp(
            description = "AWS Cloud Computing",
            details = "Treinamento Computação em nuvem",
            startDate = LocalDateTime.of(2023, 8, 25, 0, 0),
            finishDate = LocalDateTime.of(2023, 10, 10, 0, 0)
        )

        bootcampService.saveBootcamp(bootcamp)

        bootcamps = bootcampService.findAllBootcamps(page, max).toList()

        assertEquals(4, bootcamps.size)
        assertEquals("AWS Cloud Computing", bootcamps[3].content.toString())

        bootcampService.deleteBootcamp(bootcamps[3].content?.id!!)
    }

    @Test
    @Order(1)
    fun findAllBootcamps() {
        val bootcamps: List<EntityModel<BootcampDTO>> = bootcampService.findAllBootcamps(page, max).toList()

        assertEquals(3, bootcamps.size)
        assertEquals("AWS Experience", bootcamps[0].content.toString())
        assertEquals(-3, bootcamps[0].content?.id)
    }

    @Test
    @Order(2)
    fun findBootcampById() {
        val bootcamp: BootcampDTO = bootcampService.findBootcampById(-3)

        assertEquals("AWS Experience", bootcamp.toString())
        assertThrows<NoResultException> { bootcampService.findBootcampById(0) }
        assertThrows<NumberFormatException> { bootcampService.findBootcampById("aaa".toInt()) }
    }

    @Test
    @Order(4)
    fun updateBootcamp() {
        val bootcamp = Bootcamp(
            description = "TQI Go Backend",
            details = "Go backend",
            startDate = LocalDateTime.of(2023, 8, 20, 0, 0),
            finishDate = LocalDateTime.of(2023, 10, 5, 0, 0),
            id = -3
        )

        bootcampService.updateBootcamp(bootcamp)

        val bootcamps: List<EntityModel<BootcampDTO>> = bootcampService.findAllBootcamps(page, max).toList()

        assertEquals(3, bootcamps.size)
        assertEquals("TQI Go Backend", bootcamps[0].content.toString())
        assertEquals(-3, bootcamps[0].content?.id)
    }

    @Test
    @Order(5)
    fun deleteBootcamp() {
        val bootcamp = Bootcamp(
            description = "MySQL Training",
            details = "Banco de dados relacional",
            startDate = LocalDateTime.of(2023, 8, 15, 0, 0),
            finishDate = LocalDateTime.of(2023, 10, 1, 0, 0)
        )

        bootcampService.saveBootcamp(bootcamp)

        var bootcamps: List<EntityModel<BootcampDTO>> = bootcampService.findAllBootcamps(page, max).toList()

        bootcampService.deleteBootcamp(bootcamps[3].content?.id!!)

        bootcamps = bootcampService.findAllBootcamps(page, max).toList()

        assertEquals(3, bootcamps.size)
    }
}