package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Bootcamp
import jakarta.persistence.EntityManager
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayNameGeneration(DisplayNameGenerator.Simple::class)
class BootcampDaoImplTest(
    @Autowired private val entityManager: EntityManager,
    @Autowired private val bootcampDao: BootcampDao
) {
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

        bootcampDao.saveBootcamp(bootcamp)

        bootcamps = entityManager
            .createQuery("from Bootcamp order by id", Bootcamp::class.java)
            .resultList

        assertEquals(2, bootcamps.size)
        assertEquals("Linux Experience", bootcamps[1].toString())
    }

    @Test
    fun findAllBootcamps() {
        val bootcamps: List<Bootcamp> = bootcampDao.findAllBootcamps(0, 10)

        assertEquals(1, bootcamps.size)
        assertEquals("TQI Kotlin Backend", bootcamps[0].toString())
        assertEquals(-1, bootcamps[0].id)
    }

    @Test
    fun findBootcampById() {
        val bootcamp: Bootcamp = bootcampDao.findBootcampById(-1)

        assertEquals("TQI Kotlin Backend", bootcamp.toString())
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

        bootcampDao.updateBootcamp(bootcamp)

        val bootcamps: List<Bootcamp> = entityManager
            .createQuery("from Bootcamp order by id", Bootcamp::class.java)
            .resultList

        assertEquals(1, bootcamps.size)
        assertEquals("TQI Go Backend", bootcamps[0].toString())
        assertEquals(-1, bootcamps[0].id)
    }

    @Test
    fun deleteBootcamp() {
        bootcampDao.deleteBootcamp(-1)

        val bootcamps: List<Bootcamp> = entityManager
            .createQuery("from Bootcamp order by id", Bootcamp::class.java)
            .resultList

        assertEquals(0, bootcamps.size)
    }
}