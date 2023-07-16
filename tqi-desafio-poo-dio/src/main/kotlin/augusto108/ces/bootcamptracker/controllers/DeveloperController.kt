package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.entities.Developer
import augusto108.ces.bootcamptracker.services.DeveloperService
import augusto108.ces.bootcamptracker.util.MediaType
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/developers")
class DeveloperController(private val developerService: DeveloperService) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun saveDeveloper(@RequestBody developer: Developer): ResponseEntity<DeveloperDTO> {
        val savedDeveloper: DeveloperDTO = developerService.saveDeveloper(developer)
        savedDeveloper.add(linkTo(DeveloperController::class.java).slash("/${savedDeveloper.id}").withSelfRel())
        savedDeveloper.add(linkTo(DeveloperController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDeveloper)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllDevelopers(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<DeveloperDTO>> {
        val developerDTOList: List<DeveloperDTO> = developerService.findAllDevelopers(page, max)

        for (developer in developerDTOList) {
            developer.add(linkTo(DeveloperController::class.java).withSelfRel())
            developer.add(
                linkTo(DeveloperController::class.java).slash("/${developer.id}").withRel("developer${developer.id}")
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(developerDTOList)
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findDeveloperById(@PathVariable("id") id: Int): ResponseEntity<DeveloperDTO> {
        val developer: DeveloperDTO = developerService.findDeveloperById(id)
        developer.add(linkTo(DeveloperController::class.java).slash("/${developer.id}").withSelfRel())
        developer.add(linkTo(DeveloperController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(developer)
    }

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateDeveloper(@RequestBody developer: Developer): ResponseEntity<DeveloperDTO> {
        val updatedDeveloper: DeveloperDTO = developerService.updateDeveloper(developer)
        updatedDeveloper.add(linkTo(DeveloperController::class.java).slash("/${updatedDeveloper.id}").withSelfRel())
        updatedDeveloper.add(linkTo(DeveloperController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedDeveloper)
    }

    @DeleteMapping("/{id}")
    fun deleteDeveloper(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(developerService.deleteDeveloper(id))
}