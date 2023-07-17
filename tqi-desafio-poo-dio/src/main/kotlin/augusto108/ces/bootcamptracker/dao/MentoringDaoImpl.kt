package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.entities.Mentoring
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class MentoringDaoImpl(private val entityManager: EntityManager) : MentoringDao {
    override fun saveMentoring(mentoring: Mentoring): Mentoring {
        entityManager.persist(mentoring)

        return mentoring
    }

    override fun findAllMentoring(page: Int, max: Int): List<Mentoring> =
        entityManager
            .createQuery("from Mentoring order by id", Mentoring::class.java)
            .setFirstResult(page * max)
            .setMaxResults(max)
            .resultList

    override fun findMentoringById(id: Int): Mentoring =
        entityManager
            .createQuery("from Mentoring m where id = : id", Mentoring::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateMentoring(mentoring: Mentoring): Mentoring {
        var m = findMentoringById(mentoring.id)
        m = mentoring.copyProperties(m)

        entityManager.persist(m)

        return m
    }

    override fun deleteMentoring(id: Int): Any = entityManager.remove(findMentoringById(id))
}