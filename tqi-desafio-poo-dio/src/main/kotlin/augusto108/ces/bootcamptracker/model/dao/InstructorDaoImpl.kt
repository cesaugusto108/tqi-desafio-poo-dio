package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.model.helpers.PropertyDuplicate.copy
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class InstructorDaoImpl(private val entityManager: EntityManager) : InstructorDao {
    override fun saveInstructor(instructor: Instructor): Instructor {
        entityManager.persist(instructor)

        return instructor
    }

    override fun findAllInstructors(): List<Instructor> =
        entityManager
            .createQuery("from Instructor order by id", Instructor::class.java)
            .resultList

    override fun findInstructorById(id: UUID): Instructor =
        entityManager
            .createQuery("from Instructor i where id = :id", Instructor::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateInstructor(instructor: Instructor): Instructor {
        var i: Instructor? = instructor.id?.let { findInstructorById(it) }
        i = instructor.copy(i!!)

        entityManager.persist(i)

        return i
    }

    override fun deleteInstructor(id: UUID): Unit = entityManager.remove(findInstructorById(id))

    override fun activateInstructor(id: UUID) {
        entityManager
            .createNativeQuery("update `person` set `active` = b'1' where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    }

    override fun deactivateInstructor(id: UUID) {
        entityManager
            .createNativeQuery("update `person` set `active` = b'0' where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    }
}