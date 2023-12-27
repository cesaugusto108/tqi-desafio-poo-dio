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
        "from Instructor order by id".apply {
            return entityManager.createQuery(this, Instructor::class.java).resultList
        }
    }

    override fun findInstructorById(id: UUID): Instructor {
        "from Instructor i where id = :id".apply {
            return entityManager.createQuery(this, Instructor::class.java).setParameter("id", id).singleResult
        }
    }

    override fun updateInstructor(instructor: Instructor): Instructor {
        val existingInstructor: Instructor? = instructor.id?.let { findInstructorById(it) }
        instructor.copyTo(existingInstructor!!).also {
            entityManager.persist(it)
            return it
        }
    }

    override fun deleteInstructor(id: UUID): Unit = entityManager.remove(findInstructorById(id))

    override fun activateInstructor(id: UUID) {
        "update `person` set `active` = b'1' where id = :id".apply {
            entityManager.createNativeQuery(this).setParameter("id", id).executeUpdate()
        }
    }

    override fun deactivateInstructor(id: UUID) {
        "update `person` set `active` = b'0' where id = :id".apply {
            entityManager.createNativeQuery(this).setParameter("id", id).executeUpdate()
        }
    }
}