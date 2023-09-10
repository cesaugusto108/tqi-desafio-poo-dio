package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.services.DeveloperService
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${API_VERSION}developers")
class DeveloperController(private val developerService: DeveloperService) : DeveloperOperations {
    override fun saveDeveloper(developer: Developer): ResponseEntity<DeveloperDTO> {
        val savedDeveloper: DeveloperDTO = developerService.saveDeveloper(developer)
        savedDeveloper.add(linkTo(DeveloperController::class.java).slash("/${savedDeveloper.id}").withSelfRel())
        savedDeveloper.add(linkTo(DeveloperController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeveloper)
    }

    override fun findAllDevelopers(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<DeveloperDTO>>> {
        val pagedModel: PagedModel<EntityModel<DeveloperDTO>> = developerService.findAllDevelopers(page, max)
        val developerDTOList: MutableList<DeveloperDTO?> = mutableListOf()

        for (entityModel: EntityModel<DeveloperDTO> in pagedModel) developerDTOList.add(entityModel.content)

        for (developer: DeveloperDTO? in developerDTOList) {
            developer?.add(linkTo(DeveloperController::class.java).withSelfRel())
            developer?.add(
                linkTo(DeveloperController::class.java).slash("/${developer.id}").withRel("developer${developer.id}")
            )
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pagedModel)
    }

    override fun findDeveloperById(id: String): ResponseEntity<DeveloperDTO> {
        val developer: DeveloperDTO = developerService.findDeveloperById(id)
        developer.add(linkTo(DeveloperController::class.java).slash("/${developer.id}").withSelfRel())
        developer.add(linkTo(DeveloperController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(developer)
    }

    override fun updateDeveloper(developer: Developer): ResponseEntity<DeveloperDTO> {
        val updatedDeveloper: DeveloperDTO = developerService.updateDeveloper(developer)
        updatedDeveloper.add(linkTo(DeveloperController::class.java).slash("/${updatedDeveloper.id}").withSelfRel())
        updatedDeveloper.add(linkTo(DeveloperController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedDeveloper)
    }

    override fun deleteDeveloper(id: String): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(developerService.deleteDeveloper(id))

    override fun activateDeveloper(id: String): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(developerService.activateDeveloper(id))

    override fun deactivateDeveloper(id: String): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(developerService.deactivateDeveloper(id))
}