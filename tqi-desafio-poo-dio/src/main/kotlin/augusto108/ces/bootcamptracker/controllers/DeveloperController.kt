package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.services.DeveloperService
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

@RestController
class DeveloperController(private val developerService: DeveloperService) : DeveloperOperations {

    val developerControllerClass: Class<DeveloperController> = DeveloperController::class.java

    override fun saveDeveloper(developer: Developer): ResponseEntity<DeveloperDTO> {
        val savedDeveloper: DeveloperDTO = developerService.saveDeveloper(developer)
        savedDeveloper.add(linkTo(developerControllerClass).slash("/${savedDeveloper.id}").withSelfRel())
        savedDeveloper.add(linkTo(developerControllerClass).withRel("all"))
        val uri: URI = savedDeveloper.getRequiredLink(IanaLinkRelations.SELF).toUri()
        return ResponseEntity.status(201).location(uri).body(savedDeveloper)
    }

    override fun findAllDevelopers(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<DeveloperDTO>>> {
        val pagedModel: PagedModel<EntityModel<DeveloperDTO>> = developerService.findAllDevelopers(page, max)
        val developerDTOList: MutableList<DeveloperDTO?> = mutableListOf()

        for (entityModel: EntityModel<DeveloperDTO> in pagedModel) developerDTOList.add(entityModel.content)

        for (developer: DeveloperDTO? in developerDTOList) {
            val selfLink = linkTo(developerControllerClass).withSelfRel()
            val developerId: UUID? = developer?.id
            val developerRel = "developer${developer?.id}"
            val developerLink = linkTo(developerControllerClass).slash(developerId).withRel(developerRel)
            developer?.add(selfLink)
            developer?.add(developerLink)
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())
        return ResponseEntity.status(200).headers(headers).body(pagedModel)
    }

    override fun findDeveloperById(id: String): ResponseEntity<DeveloperDTO> {
        val developer: DeveloperDTO = developerService.findDeveloperById(id)
        developer.add(linkTo(developerControllerClass).slash("/${developer.id}").withSelfRel())
        developer.add(linkTo(developerControllerClass).withRel("all"))
        return ResponseEntity.status(200).body(developer)
    }

    override fun updateDeveloper(developer: Developer): ResponseEntity<DeveloperDTO> {
        val updatedDeveloper: DeveloperDTO = developerService.updateDeveloper(developer)
        updatedDeveloper.add(linkTo(developerControllerClass).slash("/${updatedDeveloper.id}").withSelfRel())
        updatedDeveloper.add(linkTo(developerControllerClass).withRel("all"))
        return ResponseEntity.status(200).body(updatedDeveloper)
    }

    override fun deleteDeveloper(id: String): ResponseEntity<Unit> {
        developerService.deleteDeveloper(id)
        return ResponseEntity.status(204).build()
    }

    override fun activateDeveloper(id: String): ResponseEntity<Unit> {
        developerService.activateDeveloper(id)
        return ResponseEntity.status(204).build()
    }

    override fun deactivateDeveloper(id: String): ResponseEntity<Unit> {
        developerService.deactivateDeveloper(id)
        return ResponseEntity.status(204).build()
    }
}