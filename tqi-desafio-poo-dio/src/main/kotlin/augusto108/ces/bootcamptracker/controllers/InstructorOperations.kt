package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.instructor.*
import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.util.API_VERSION
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Instructors", description = "endpoints to manage instructors information")
@RequestMapping("${API_VERSION}instructors")
interface InstructorOperations {

    @ResponseStatus(HttpStatus.CREATED)
    @SaveOperation
    @PostMapping(consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun saveInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO>

    @ResponseStatus(HttpStatus.OK)
    @FindAllOperation
    @GetMapping(produces = [MediaType.JSON, MediaType.YAML])
    fun findAllInstructors(
        @RequestParam(defaultValue = "0", required = false, value = "page") page: Int,
        @RequestParam(defaultValue = "10", required = false, value = "size") max: Int
    ): ResponseEntity<PagedModel<EntityModel<InstructorDTO>>>

    @ResponseStatus(HttpStatus.OK)
    @FindByIdOperation
    @GetMapping("/{id}", produces = [MediaType.JSON, MediaType.YAML])
    fun findInstructorById(@PathVariable("id") id: String): ResponseEntity<InstructorDTO>

    @ResponseStatus(HttpStatus.OK)
    @UpdateOperation
    @PutMapping("/{id}", consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun updateInstructor(
        @PathVariable("id") id: String,
        @RequestBody instructor: Instructor
    ): ResponseEntity<InstructorDTO>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteOperation
    @DeleteMapping("/{id}")
    fun deleteInstructor(@PathVariable("id") id: String): ResponseEntity<Unit>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ActivateInstructor
    @PatchMapping("/active/{id}")
    fun activateInstructor(@PathVariable("id") id: String): ResponseEntity<Unit>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeactivateInstructor
    @PatchMapping("/inactive/{id}")
    fun deactivateInstructor(@PathVariable("id") id: String): ResponseEntity<Unit>
}