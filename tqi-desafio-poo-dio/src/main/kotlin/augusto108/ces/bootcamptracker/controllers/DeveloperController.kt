package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.entities.Developer
import augusto108.ces.bootcamptracker.services.DeveloperService
import augusto108.ces.bootcamptracker.util.MediaType
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
    fun saveDeveloper(@RequestBody developer: Developer): ResponseEntity<DeveloperDTO> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(developerService.saveDeveloper(developer))

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllDevelopers(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<DeveloperDTO>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(developerService.findAllDevelopers(page, max))

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findDeveloperById(@PathVariable("id") id: Int): ResponseEntity<DeveloperDTO> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(developerService.findDeveloperById(id))

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateDeveloper(@RequestBody developer: Developer): ResponseEntity<DeveloperDTO> {
        val d: Developer = developerService.developerById(developer.id)

        d.level = developer.level
        d.name = developer.name
        d.username = developer.username
        d.password = developer.password
        d.age = developer.age
        d.email = developer.email
        d.bootcamps = developer.bootcamps

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(developerService.updateDeveloper(d))
    }

    @DeleteMapping("/{id}")
    fun deleteDeveloper(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(developerService.deleteDeveloper(id))
}