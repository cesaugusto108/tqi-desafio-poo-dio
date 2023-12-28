package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.developer.*
import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.util.API_VERSION
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Developers", description = "endpoints to manage developers information")
@RequestMapping("${API_VERSION}developers")
interface DeveloperOperations {

    @ResponseStatus(HttpStatus.CREATED)
    @SaveOperation
    @PostMapping(consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun saveDeveloper(@RequestBody developer: Developer): ResponseEntity<DeveloperDTO>

    @ResponseStatus(HttpStatus.OK)
    @FindAllOperation
    @GetMapping(produces = [MediaType.JSON, MediaType.YAML])
    fun findAllDevelopers(
        @RequestParam(defaultValue = "0", required = false, value = "page") page: Int,
        @RequestParam(defaultValue = "10", required = false, value = "size") max: Int
    ): ResponseEntity<PagedModel<EntityModel<DeveloperDTO>>>

    @ResponseStatus(HttpStatus.OK)
    @FindByIdOperation
    @GetMapping("/{id}", produces = [MediaType.JSON, MediaType.YAML])
    fun findDeveloperById(@PathVariable("id") id: String): ResponseEntity<DeveloperDTO>

    @ResponseStatus(HttpStatus.OK)
    @UpdateOperation
    @PutMapping("/{id}", consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun updateDeveloper(@PathVariable("id") id: String, @RequestBody developer: Developer): ResponseEntity<DeveloperDTO>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteOperation
    @DeleteMapping("/{id}")
    fun deleteDeveloper(@PathVariable("id") id: String): ResponseEntity<Unit>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ActivateDeveloper
    @PatchMapping("/active/{id}")
    fun activateDeveloper(@PathVariable("id") id: String): ResponseEntity<Unit>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeactivateDeveloper
    @PatchMapping("/inactive/{id}")
    fun deactivateDeveloper(@PathVariable("id") id: String): ResponseEntity<Unit>
}