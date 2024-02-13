package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.model.helpers.PropertyDuplicate.copyTo
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@Repository
class BootcampDaoImpl @Autowired constructor(private val entityManager: EntityManager) : BootcampDao {

    override fun saveBootcamp(bootcamp: Bootcamp): Bootcamp {
        entityManager.persist(bootcamp)
        return bootcamp
    }

    override fun findAllBootcamps(): List<Bootcamp> {
        "from Bootcamp order by id".apply { return entityManager.createQuery(this, Bootcamp::class.java).resultList }
    }

    override fun findBootcampById(id: Int): Bootcamp {
        "from Bootcamp b where id = :id".apply {
            return entityManager.createQuery(this, Bootcamp::class.java).setParameter("id", id).singleResult
        }
    }

    override fun updateBootcamp(bootcamp: Bootcamp): Bootcamp {
        val existingBootcamp: Bootcamp = findBootcampById(bootcamp.id)
        bootcamp.copyTo(existingBootcamp).also {
            entityManager.persist(it)
            return it
        }
    }

    override fun deleteBootcamp(id: Int): Unit = entityManager.remove(findBootcampById(id))
}