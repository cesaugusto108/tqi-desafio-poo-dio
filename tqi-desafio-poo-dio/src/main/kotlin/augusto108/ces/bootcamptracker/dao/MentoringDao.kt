package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Mentoring

interface MentoringDao {
    fun saveMentoring(mentoring: Mentoring): Mentoring

    fun findAllMentoring(): List<Mentoring>

    fun findMentoringById(id: Int): Mentoring

    fun updateMentoring(mentoring: Mentoring): Mentoring

    fun deleteMentoring(id: Int): Any
}