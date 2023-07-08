package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.entities.Mentoring

interface MentoringService {
    fun saveMentoring(mentoring: Mentoring): Mentoring

    fun findAllMentoring(page: Int, max: Int): List<Mentoring>

    fun findMentoringById(id: Int): Mentoring

    fun updateMentoring(mentoring: Mentoring): Mentoring

    fun deleteMentoring(id: Int): Any
}