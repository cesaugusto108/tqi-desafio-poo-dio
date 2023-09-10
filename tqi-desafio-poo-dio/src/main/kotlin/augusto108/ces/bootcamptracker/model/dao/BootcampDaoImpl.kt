package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.model.helpers.PropertyDuplicate.copyTo
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class BootcampDaoImpl(private val entityManager: EntityManager) : BootcampDao {
    override fun saveBootcamp(bootcamp: Bootcamp): Bootcamp {
        entityManager.persist(bootcamp)

        return bootcamp
    }

    override fun findAllBootcamps(): List<Bootcamp> =
        entityManager
            .createQuery("from Bootcamp order by id", Bootcamp::class.java)
            .resultList

    override fun findBootcampById(id: Int): Bootcamp =
        entityManager
            .createQuery("from Bootcamp b where id = :id", Bootcamp::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateBootcamp(bootcamp: Bootcamp): Bootcamp {
        val existingBootcamp: Bootcamp = findBootcampById(bootcamp.id)
        val updatedBootcamp: Bootcamp = bootcamp.copyTo(existingBootcamp)

        entityManager.persist(updatedBootcamp)

        return updatedBootcamp
    }

    override fun deleteBootcamp(id: Int): Unit = entityManager.remove(findBootcampById(id))
}