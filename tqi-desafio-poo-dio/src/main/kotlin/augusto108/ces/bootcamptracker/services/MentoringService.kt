package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.dto.MentoringDTO
import augusto108.ces.bootcamptracker.model.entities.Mentoring
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel

interface MentoringService {

    fun saveMentoring(mentoring: Mentoring): MentoringDTO

    fun findAllMentoring(page: Int, max: Int): PagedModel<EntityModel<MentoringDTO>>

    fun findMentoringById(id: Int): MentoringDTO

    fun mentoringById(id: Int): Mentoring

    fun updateMentoring(mentoring: Mentoring): MentoringDTO

    fun deleteMentoring(id: Int)
}