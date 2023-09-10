package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.developer.*
import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Developers", description = "endpoints to manage developers information")
interface DeveloperOperations {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @SaveOperation
    fun saveDeveloper(@RequestBody developer: Developer): ResponseEntity<DeveloperDTO>

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindAllOperation
    fun findAllDevelopers(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<PagedModel<EntityModel<DeveloperDTO>>>

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindByIdOperation
    fun findDeveloperById(@PathVariable("id") id: String): ResponseEntity<DeveloperDTO>

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @UpdateOperation
    fun updateDeveloper(@RequestBody developer: Developer): ResponseEntity<DeveloperDTO>

    @DeleteMapping("/{id}")
    @DeleteOperation
    fun deleteDeveloper(@PathVariable("id") id: String): ResponseEntity<Unit>

    @PatchMapping("/active/{id}")
    @ActivateDeveloper
    fun activateDeveloper(@PathVariable("id") id: String): ResponseEntity<Unit>

    @PatchMapping("/inactive/{id}")
    @DeactivateDeveloper
    fun deactivateDeveloper(@PathVariable("id") id: String): ResponseEntity<Unit>
}