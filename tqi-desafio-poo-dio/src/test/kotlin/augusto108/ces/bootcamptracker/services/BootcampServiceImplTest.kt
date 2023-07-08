package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.entities.Bootcamp
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
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
@TestPropertySource("classpath:app_params.properties")
class BootcampServiceImplTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val bootcampService: BootcampService
) {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @BeforeEach
    fun setUp() {
        val bootcampQuery: String =
            "insert into " +
                    "`bootcamp` (`id`, `bootcamp_description`, `bootcamp_details`, `finish_date`, `start_date`)" +
                    " values (-1, 'TQI Kotlin Backend', 'Java e Kotlin backend', NULL, NULL);"

        entityManager.createNativeQuery(bootcampQuery, Bootcamp::class.java).executeUpdate()
    }

    @AfterEach
    fun tearDown() {
        entityManager.createNativeQuery("delete from `bootcamp`;")
    }

    @Test
    fun saveBootcamp() {
        var bootcamps: List<Bootcamp> = entityManager
            .createQuery("from Bootcamp order by id", Bootcamp::class.java)
            .resultList

        assertEquals(1, bootcamps.size)
        assertEquals("TQI Kotlin Backend", bootcamps[0].toString())
        assertEquals(-1, bootcamps[0].id)

        val bootcamp = Bootcamp(
            description = "Linux Experience",
            details = "Aperfeiçoamento Linux",
            startDate = LocalDateTime.of(2023, 8, 20, 0, 0),
            finishDate = LocalDateTime.of(2023, 10, 5, 0, 0)
        )

        bootcampService.saveBootcamp(bootcamp)

        bootcamps = entityManager
            .createQuery("from Bootcamp order by id", Bootcamp::class.java)
            .resultList

        assertEquals(2, bootcamps.size)
        assertEquals("Linux Experience", bootcamps[1].toString())
    }

    @Test
    fun findAllBootcamps() {
        val bootcamps: List<Bootcamp> = bootcampService.findAllBootcamps(page, max)

        assertEquals(1, bootcamps.size)
        assertEquals("TQI Kotlin Backend", bootcamps[0].toString())
        assertEquals(-1, bootcamps[0].id)
    }

    @Test
    fun findBootcampById() {
        val bootcamp: Bootcamp = bootcampService.findBootcampById(-1)

        assertEquals("TQI Kotlin Backend", bootcamp.toString())
        assertThrows<NoResultException> { bootcampService.findBootcampById(0) }
        assertThrows<NumberFormatException> { bootcampService.findBootcampById("aaa".toInt()) }
    }

    @Test
    fun updateBootcamp() {
        val bootcamp = Bootcamp(
            description = "TQI Go Backend",
            details = "Go backend",
            startDate = LocalDateTime.of(2023, 8, 20, 0, 0),
            finishDate = LocalDateTime.of(2023, 10, 5, 0, 0),
            id = -1
        )

        bootcampService.updateBootcamp(bootcamp)

        val bootcamps: List<Bootcamp> = entityManager
            .createQuery("from Bootcamp order by id", Bootcamp::class.java)
            .resultList

        assertEquals(1, bootcamps.size)
        assertEquals("TQI Go Backend", bootcamps[0].toString())
        assertEquals(-1, bootcamps[0].id)
    }

    @Test
    fun deleteBootcamp() {
        bootcampService.deleteBootcamp(-1)

        val bootcamps: List<Bootcamp> = entityManager
            .createQuery("from Bootcamp order by id", Bootcamp::class.java)
            .resultList

        assertEquals(0, bootcamps.size)
    }
}