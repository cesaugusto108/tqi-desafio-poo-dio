package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.bootcamp.*
import augusto108.ces.bootcamptracker.model.dto.BootcampDTO
import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Bootcamps", description = "endpoints to manage bootcamps information")
interface BootcampOperations {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @SaveOperation
    fun saveBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<BootcampDTO>

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindAllOperation
    fun findAllBootcamps(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<BootcampDTO>>

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindByIdOperation
    fun findBootcampById(@PathVariable("id") id: Int): ResponseEntity<BootcampDTO>

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @UpdateOperation
    fun updateBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<BootcampDTO>

    @DeleteMapping("/{id}")
    @DeleteOperation
    fun deleteBootcamp(@PathVariable("id") id: Int): ResponseEntity<Unit>
}