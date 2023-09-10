package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel

interface DeveloperService {
    fun saveDeveloper(developer: Developer): DeveloperDTO

    fun findAllDevelopers(page: Int, max: Int): PagedModel<EntityModel<DeveloperDTO>>

    fun findDeveloperById(id: String): DeveloperDTO

    fun developerById(id: String): Developer

    fun updateDeveloper(developer: Developer): DeveloperDTO

    fun deleteDeveloper(id: String)

    fun activateDeveloper(id: String)

    fun deactivateDeveloper(id: String)
}