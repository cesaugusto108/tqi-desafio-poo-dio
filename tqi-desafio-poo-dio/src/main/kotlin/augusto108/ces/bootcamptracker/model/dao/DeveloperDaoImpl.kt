package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Developer
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

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

    override fun findDeveloperById(id: Int): Developer =
        entityManager
            .createQuery("from Developer d where id = :id", Developer::class.java)
            .setParameter("id", id)
            .singleResult

    override fun updateDeveloper(developer: Developer): Developer {
        var d: Developer = findDeveloperById(developer.id)
        d = developer.copyProperties(d)

        entityManager.persist(d)

        return d
    }

    override fun deleteDeveloper(id: Int): Unit = entityManager.remove(findDeveloperById(id))

    override fun activateDeveloper(id: Int) {
        entityManager
            .createNativeQuery("update `person` set `active` = b'1' where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    }

    override fun deactivateDeveloper(id: Int) {
        entityManager
            .createNativeQuery("update `person` set `active` = b'0' where id = :id")
            .setParameter("id", id)
            .executeUpdate()
    }
}