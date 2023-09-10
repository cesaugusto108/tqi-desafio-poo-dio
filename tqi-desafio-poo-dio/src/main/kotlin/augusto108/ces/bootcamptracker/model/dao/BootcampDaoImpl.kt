package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.model.helpers.PropertyDuplicate.copy
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
        var b: Bootcamp = findBootcampById(bootcamp.id)
        b = bootcamp.copy(b)

        entityManager.persist(b)

        return b
    }

    override fun deleteBootcamp(id: Int): Unit = entityManager.remove(findBootcampById(id))
}