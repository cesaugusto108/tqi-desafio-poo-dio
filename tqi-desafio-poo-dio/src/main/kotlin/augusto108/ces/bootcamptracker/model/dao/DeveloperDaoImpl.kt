package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.model.helpers.PropertyDuplicate.copy
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class DeveloperDaoImpl(private val entityManager: EntityManager) : DeveloperDao {
    override fun saveDeveloper(developer: Developer): Developer {
        entityManager.persist(developer)

        return developer
    }

    override fun findAllDevelopers(): List<Developer> =
        entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .resultList

    override fun findDeveloperById(id: UUID): Developer =
        entityManager
            .createQuery("from Developer d where id = :id", Developer::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateDeveloper(developer: Developer): Developer {
        var d: Developer? = developer.id?.let { findDeveloperById(it) }
        d = developer.copy(d!!)

        entityManager.persist(d)

        return d
    }

    override fun deleteDeveloper(id: UUID): Unit = entityManager.remove(findDeveloperById(id))

    override fun activateDeveloper(id: UUID) {
        entityManager
            .createNativeQuery("update `person` set `active` = b'1' where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    }

    override fun deactivateDeveloper(id: UUID) {
        entityManager
            .createNativeQuery("update `person` set `active` = b'0' where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    }
}