package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Mentoring
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class MentoringDaoImpl(private val entityManager: EntityManager) : MentoringDao {
    override fun saveMentoring(mentoring: Mentoring): Mentoring {
        entityManager.persist(mentoring)

        return mentoring
    }

    override fun findAllMentoring(): List<Mentoring> =
        entityManager.createQuery("from Mentoring order by id", Mentoring::class.java).resultList

    override fun findMentoringById(id: Int): Mentoring =
        entityManager
            .createQuery("from Mentoring m where id = : id", Mentoring::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateMentoring(mentoring: Mentoring): Mentoring {
        val m = findMentoringById(mentoring.id)
        m.date = mentoring.date
        m.hours = mentoring.hours
        m.details = mentoring.details
        m.description = mentoring.description

        entityManager.persist(m)

        return m
    }

    override fun deleteMentoring(id: Int): Any = entityManager.remove(findMentoringById(id))
}