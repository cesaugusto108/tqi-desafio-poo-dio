package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.entities.Developer

interface DeveloperService {
    fun saveDeveloper(developer: Developer): DeveloperDTO

    fun findAllDevelopers(page: Int, max: Int): List<DeveloperDTO>

    fun findDeveloperById(id: Int): DeveloperDTO

    fun developerById(id: Int): Developer

    fun updateDeveloper(developer: Developer): DeveloperDTO

    fun deleteDeveloper(id: Int): Any
}