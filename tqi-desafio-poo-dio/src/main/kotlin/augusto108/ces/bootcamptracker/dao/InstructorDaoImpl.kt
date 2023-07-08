package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.entities.Instructor
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class InstructorDaoImpl(private val entityManager: EntityManager) : InstructorDao {
    override fun saveInstructor(instructor: Instructor): Instructor {
        entityManager.persist(instructor)

        return instructor
    }

    override fun findAllInstructors(page: Int, max: Int): List<Instructor> =
        entityManager
            .createQuery("from Instructor order by id", Instructor::class.java)
            .setFirstResult(page * max)
            .setMaxResults(max).resultList

    override fun findInstructorById(id: Int): Instructor =
        entityManager
            .createQuery("from Instructor i where id = :id", Instructor::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateInstructor(instructor: Instructor): Instructor {
        val i: Instructor = findInstructorById(instructor.id)

        i.name = instructor.name
        i.age = instructor.age
        i.email = instructor.email
        i.username = instructor.username
        i.password = instructor.password

        entityManager.persist(i)

        return i
    }

    override fun deleteInstructor(id: Int): Any = entityManager.remove(findInstructorById(id))
}