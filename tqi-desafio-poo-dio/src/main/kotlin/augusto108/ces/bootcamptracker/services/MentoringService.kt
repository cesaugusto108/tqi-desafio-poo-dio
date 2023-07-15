package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dto.MentoringDTO
import augusto108.ces.bootcamptracker.entities.Mentoring

interface MentoringService {
    fun saveMentoring(mentoring: Mentoring): MentoringDTO

    fun findAllMentoring(page: Int, max: Int): List<MentoringDTO>

    fun findMentoringById(id: Int): MentoringDTO

    fun mentoringById(id: Int): Mentoring

    fun updateMentoring(mentoring: Mentoring): MentoringDTO

    fun deleteMentoring(id: Int): Any
}