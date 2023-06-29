package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Bootcamp
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class BootcampDaoImpl(private val entityManager: EntityManager) : BootcampDao {
    override fun saveBootcamp(bootcamp: Bootcamp): Bootcamp {
        entityManager.persist(bootcamp)

        return bootcamp
    }

    override fun findAllBootcamps(): List<Bootcamp> =
        entityManager.createQuery("from Bootcamp order by id", Bootcamp::class.java).resultList

    override fun findBootcampById(id: Int): Bootcamp =
        entityManager
            .createQuery("from Bootcamp b where id = :id", Bootcamp::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateBootcamp(bootcamp: Bootcamp): Bootcamp {
        val b: Bootcamp = findBootcampById(bootcamp.id)
        b.description = bootcamp.description
        b.details = bootcamp.details
        b.activities = bootcamp.activities
        b.startDate = bootcamp.startDate
        b.finishDate = bootcamp.finishDate

        entityManager.persist(b)

        return b
    }

    override fun deleteBootcamp(id: Int): Any = entityManager.remove(findBootcampById(id))
}