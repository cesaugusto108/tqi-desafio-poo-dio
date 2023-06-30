package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.Developer
import augusto108.ces.bootcamptracker.services.DeveloperService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/developers")
class DeveloperController(private val developerService: DeveloperService) {
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun saveDeveloper(@RequestBody developer: Developer): ResponseEntity<Developer> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(developerService.saveDeveloper(developer))

    @GetMapping(produces = ["application/json"])
    fun findAllDevelopers(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<Developer>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(developerService.findAllDevelopers(page, max))

    @GetMapping("/{id}", produces = ["application/json"])
    fun findDeveloperById(@PathVariable("id") id: Int): ResponseEntity<Developer> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(developerService.findDeveloperById(id))

    @PutMapping(consumes = ["application/json"], produces = ["application/json"])
    fun updateDeveloper(@RequestBody developer: Developer): ResponseEntity<Developer> {
        val d: Developer = developerService.findDeveloperById(developer.id)
        d.level = developer.level
        d.name = developer.name
        d.age = developer.age
        d.email = developer.email
        d.bootcamps = developer.bootcamps

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(developerService.updateDeveloper(d))
    }

    @DeleteMapping("/{id}")
    fun deleteDeveloper(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(developerService.deleteDeveloper(id))
}