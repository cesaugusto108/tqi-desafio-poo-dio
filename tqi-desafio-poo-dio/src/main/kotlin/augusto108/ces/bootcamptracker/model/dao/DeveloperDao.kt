package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Developer

interface DeveloperDao {
    fun saveDeveloper(developer: Developer): Developer

    fun findAllDevelopers(page: Int, max: Int): List<Developer>

    fun findDeveloperById(id: Int): Developer

    fun updateDeveloper(developer: Developer): Developer

    fun deleteDeveloper(id: Int)

    fun activateDeveloper(id: Int)

    fun deactivateDeveloper(id: Int)
}