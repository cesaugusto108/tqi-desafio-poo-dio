package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.bootcamp.*
import augusto108.ces.bootcamptracker.model.dto.BootcampDTO
import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.util.API_VERSION
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Bootcamps", description = "endpoints to manage bootcamps information")
@RequestMapping("${API_VERSION}bootcamps")
interface BootcampOperations {

    @ResponseStatus(HttpStatus.CREATED)
    @SaveOperation
    @PostMapping(consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun saveBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<BootcampDTO>

    @ResponseStatus(HttpStatus.OK)
    @FindAllOperation
    @GetMapping(produces = [MediaType.JSON, MediaType.YAML])
    fun findAllBootcamps(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<PagedModel<EntityModel<BootcampDTO>>>

    @ResponseStatus(HttpStatus.OK)
    @FindByIdOperation
    @GetMapping("/{id}", produces = [MediaType.JSON, MediaType.YAML])
    fun findBootcampById(@PathVariable("id") id: Int): ResponseEntity<BootcampDTO>

    @ResponseStatus(HttpStatus.OK)
    @UpdateOperation
    @PutMapping("/{id}", consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun updateBootcamp(@PathVariable("id") id: Int, @RequestBody bootcamp: Bootcamp): ResponseEntity<BootcampDTO>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteOperation
    @DeleteMapping("/{id}")
    fun deleteBootcamp(@PathVariable("id") id: Int): ResponseEntity<Unit>
}