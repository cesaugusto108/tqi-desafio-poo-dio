package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.TestContainersConfig
import augusto108.ces.bootcamptracker.model.dto.MentoringDTO
import augusto108.ces.bootcamptracker.model.entities.Mentoring
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
class MentoringServiceImplTests(@Autowired private val mentoringService: MentoringService) : TestContainersConfig() {
    @Value("\${page.value}")
    var page: Int = 0

    @Value("\${max.value}")
    var max: Int = 0

    @Test
    fun saveMentoring() {
        var mentorings: List<MentoringDTO> = mentoringService.findAllMentoring(page, max)

        assertEquals(2, mentorings.size)
        assertEquals("Java - POO (mentoring)", mentorings[0].toString())
        assertEquals(-2, mentorings[0].id)

        val mentoring =
            Mentoring(date = null, hours = null, description = "Extreme Programming", details = "Gestão de times ágeis")

        mentoringService.saveMentoring(mentoring)

        mentorings = mentoringService.findAllMentoring(page, max)

        assertEquals(3, mentorings.size)
        assertEquals("Extreme Programming (mentoring)", mentorings[2].toString())

        mentoringService.deleteMentoring(mentorings[2].id)
    }

    @Test
    fun findAllMentoring() {
        val mentorings: List<MentoringDTO> = mentoringService.findAllMentoring(page, max)

        assertEquals(2, mentorings.size)
        assertEquals("Kotlin - POO (mentoring)", mentorings[1].toString())
        assertEquals(-1, mentorings[1].id)
    }

    @Test
    fun findMentoringById() {
        val mentoring: MentoringDTO = mentoringService.findMentoringById(-2)

        assertEquals("Java - POO (mentoring)", mentoring.toString())
        assertThrows<NoResultException> { mentoringService.findMentoringById(0) }
        assertThrows<NumberFormatException> { mentoringService.findMentoringById("aaa".toInt()) }
    }

    @Test
    fun updateMentoring() {
        val mentoring =
            Mentoring(date = null, hours = null, description = "Agile", details = "Gestão de times ágeis", id = -1)

        mentoringService.updateMentoring(mentoring)

        val mentorings: List<MentoringDTO> = mentoringService.findAllMentoring(page, max)

        assertEquals(2, mentorings.size)
        assertEquals("Agile (mentoring)", mentorings[1].toString())
        assertEquals(-1, mentorings[1].id)
    }

    @Test
    fun deleteMentoring() {
        val mentoring =
            Mentoring(date = null, hours = null, description = "Spring Security", details = "Security with Spring")

        mentoringService.saveMentoring(mentoring)

        var mentoringList: List<MentoringDTO> = mentoringService.findAllMentoring(page, max)

        mentoringService.deleteMentoring(mentoringList[2].id)

        mentoringList = mentoringService.findAllMentoring(page, max)

        assertEquals(2, mentoringList.size)
    }
}