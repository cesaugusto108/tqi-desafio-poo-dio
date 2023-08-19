package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Instructor
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

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

    override fun findInstructorById(id: Int): Instructor =
        entityManager
            .createQuery("from Instructor i where id = :id", Instructor::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateInstructor(instructor: Instructor): Instructor {
        var i: Instructor = findInstructorById(instructor.id)
        i = instructor.copyProperties(i)

        entityManager.persist(i)

        return i
    }

    override fun deleteInstructor(id: Int): Unit = entityManager.remove(findInstructorById(id))

    override fun activateInstructor(id: Int) {
        entityManager
            .createNativeQuery("update `person` set `active` = b'1' where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    }

    override fun deactivateInstructor(id: Int) {
        entityManager
            .createNativeQuery("update `person` set `active` = b'0' where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    }
}