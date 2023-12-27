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

    override fun findAllMentoring(): List<Mentoring> {
        "from Mentoring order by id".apply { return entityManager.createQuery(this, Mentoring::class.java).resultList }

    }

    override fun findMentoringById(id: Int): Mentoring {
        "from Mentoring m where id = : id".apply {
            return entityManager.createQuery(this, Mentoring::class.java).setParameter("id", id).singleResult
        }
    }

    override fun updateMentoring(mentoring: Mentoring): Mentoring {
        val existingMentoring: Mentoring = findMentoringById(mentoring.id)
        mentoring.copyTo(existingMentoring).also {
            entityManager.persist(it)
            return it
        }
    }

    override fun deleteMentoring(id: Int): Unit = entityManager.remove(findMentoringById(id))
}