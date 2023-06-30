package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.Developer

interface DeveloperService {
    fun saveDeveloper(developer: Developer): Developer

    fun findAllDevelopers(page: Int, max: Int): List<Developer>

    fun findDeveloperById(id: Int): Developer

    fun updateDeveloper(developer: Developer): Developer

    fun deleteDeveloper(id: Int): Any
}