package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.entities.Developer

interface DeveloperDao {
    fun saveDeveloper(developer: Developer): Developer

    fun findAllDevelopers(page: Int, max: Int): List<Developer>

    fun findDeveloperById(id: Int): Developer

    fun updateDeveloper(developer: Developer): Developer

    fun deleteDeveloper(id: Int): Any
}