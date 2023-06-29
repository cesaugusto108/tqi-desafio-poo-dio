package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Developer

interface DeveloperDao {
    fun saveDeveloper(developer: Developer): Developer

    fun findAllDevelopers(): List<Developer>

    fun findDeveloperById(id: Int): Developer

    fun updateDeveloper(developer: Developer): Developer

    fun deleteDeveloper(id: Int): Any
}