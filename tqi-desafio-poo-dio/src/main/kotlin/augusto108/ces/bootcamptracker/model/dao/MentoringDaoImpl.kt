package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Mentoring
import augusto108.ces.bootcamptracker.model.helpers.PropertyDuplicate.copyTo
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class MentoringDaoImpl(private val entityManager: EntityManager) : MentoringDao {
    override fun saveMentoring(mentoring: Mentoring): Mentoring {
        entityManager.persist(mentoring)

        return mentoring
    }

    override fun findAllMentoring(): List<Mentoring> =
        entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .resultList

    override fun findMentoringById(id: Int): Mentoring =
        entityManager
            .createQuery("from Mentoring m where id = : id", Mentoring::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateMentoring(mentoring: Mentoring): Mentoring {
        val existingMentoring: Mentoring = findMentoringById(mentoring.id)
        val updatedMentoring: Mentoring = mentoring.copyTo(existingMentoring)

        entityManager.persist(updatedMentoring)

        return updatedMentoring
    }

    override fun deleteMentoring(id: Int): Unit = entityManager.remove(findMentoringById(id))
}