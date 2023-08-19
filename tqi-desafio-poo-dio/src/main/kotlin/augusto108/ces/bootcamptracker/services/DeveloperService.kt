package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel

interface DeveloperService {
    fun saveDeveloper(developer: Developer): DeveloperDTO

    fun findAllDevelopers(page: Int, max: Int): PagedModel<EntityModel<DeveloperDTO>>

    fun findDeveloperById(id: Int): DeveloperDTO

    fun developerById(id: Int): Developer

    fun updateDeveloper(developer: Developer): DeveloperDTO

    fun deleteDeveloper(id: Int)

    fun activateDeveloper(id: Int)

    fun deactivateDeveloper(id: Int)
}