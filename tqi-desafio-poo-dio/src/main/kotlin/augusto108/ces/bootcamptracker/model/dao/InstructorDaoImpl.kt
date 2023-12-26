package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.model.helpers.PropertyDuplicate.copyTo
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InstructorDaoImpl(private val entityManager: EntityManager) : InstructorDao {

    override fun saveInstructor(instructor: Instructor): Instructor {
        entityManager.persist(instructor)
        return instructor
    }

    override fun findAllInstructors(): List<Instructor> {
        val query = "from Instructor order by id"
        return entityManager.createQuery(query, Instructor::class.java).resultList
    }

    override fun findInstructorById(id: UUID): Instructor {
        val query = "from Instructor i where id = :id"
        return entityManager.createQuery(query, Instructor::class.java).setParameter("id", id).singleResult
    }

    override fun updateInstructor(instructor: Instructor): Instructor {
        val existingInstructor: Instructor? = instructor.id?.let { findInstructorById(it) }
        val updatedInstructor: Instructor = instructor.copyTo(existingInstructor!!)
        entityManager.persist(updatedInstructor)
        return updatedInstructor
    }

    override fun deleteInstructor(id: UUID): Unit = entityManager.remove(findInstructorById(id))

    override fun activateInstructor(id: UUID) {
        val query = "update `person` set `active` = b'1' where id = :id"
        entityManager.createNativeQuery(query).setParameter("id", id).executeUpdate()
    }

    override fun deactivateInstructor(id: UUID) {
        val query = "update `person` set `active` = b'0' where id = :id"
        entityManager.createNativeQuery(query).setParameter("id", id).executeUpdate()
    }
}