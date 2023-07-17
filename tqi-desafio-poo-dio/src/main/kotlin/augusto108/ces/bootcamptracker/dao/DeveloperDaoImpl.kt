package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.entities.Developer
import jakarta.persistence.EntityManager
import org.springframework.stereotype.Repository

@Repository
class DeveloperDaoImpl(private val entityManager: EntityManager) : DeveloperDao {
    override fun saveDeveloper(developer: Developer): Developer {
        entityManager.persist(developer)

        return developer
    }

    override fun findAllDevelopers(page: Int, max: Int): List<Developer> =
        entityManager
            .createQuery("from Developer order by id", Developer::class.java)
            .setFirstResult(page * max)
            .setMaxResults(max)
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

    override fun deleteDeveloper(id: Int): Any = entityManager.remove(findDeveloperById(id))
}