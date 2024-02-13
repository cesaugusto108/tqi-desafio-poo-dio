package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.exceptions.UnmatchedIdException
import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.services.DeveloperService
import org.springframework.beans.factory.annotation.Autowired
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
class DeveloperController @Autowired constructor(private val developerService: DeveloperService) : DeveloperOperations {

    val developerControllerClass: Class<DeveloperController> = DeveloperController::class.java

    override fun saveDeveloper(developer: Developer): ResponseEntity<DeveloperDTO> {
        developerService.saveDeveloper(developer).also {
            it.add(linkTo(developerControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(developerControllerClass).withRel("all"))
            val uri: URI = it.getRequiredLink(IanaLinkRelations.SELF).toUri()
            return ResponseEntity.status(201).location(uri).body(it)
        }
    }

    override fun findAllDevelopers(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<DeveloperDTO>>> {
        developerService.findAllDevelopers(page, max).also {
            val developerDTOList: MutableList<DeveloperDTO?> = mutableListOf()
            for (entityModel: EntityModel<DeveloperDTO> in it) developerDTOList.add(entityModel.content)
            for (developer: DeveloperDTO? in developerDTOList) {
                val selfLink = linkTo(developerControllerClass).withSelfRel()
                val developerId: UUID? = developer?.id
                val developerRel = "developer${developer?.id}"
                val developerLink = linkTo(developerControllerClass).slash(developerId).withRel(developerRel)
                developer?.add(selfLink)
                developer?.add(developerLink)
            }
            val headers = HttpHeaders()
            headers.add("X-Page-Number", it.metadata?.number.toString())
            headers.add("X-Page-Size", it.metadata?.size.toString())
            return ResponseEntity.status(200).headers(headers).body(it)
        }
    }

    override fun findDeveloperById(id: String): ResponseEntity<DeveloperDTO> {
        developerService.findDeveloperById(id).also {
            it.add(linkTo(developerControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(developerControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
    }

    override fun updateDeveloper(id: String, developer: Developer): ResponseEntity<DeveloperDTO> {
        if (id == developer.id.toString()) developerService.updateDeveloper(developer).also {
            it.add(linkTo(developerControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(developerControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
        else throw UnmatchedIdException("Path id and request body id do not match")
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