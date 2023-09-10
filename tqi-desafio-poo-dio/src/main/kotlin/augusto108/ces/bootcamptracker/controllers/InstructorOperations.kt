package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.instructor.*
import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Instructors", description = "endpoints to manage instructors information")
interface InstructorOperations {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @SaveOperation
    fun saveInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO>

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindAllOperation
    fun findAllInstructors(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<PagedModel<EntityModel<InstructorDTO>>>

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindByIdOperation
    fun findInstructorById(@PathVariable("id") id: String): ResponseEntity<InstructorDTO>

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @UpdateOperation
    fun updateInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO>

    @DeleteMapping("/{id}")
    @DeleteOperation
    fun deleteInstructor(@PathVariable("id") id: String): ResponseEntity<Unit>

    @PatchMapping("/active/{id}")
    @ActivateInstructor
    fun activateInstructor(@PathVariable("id") id: String): ResponseEntity<Unit>

    @PatchMapping("/inactive/{id}")
    @DeactivateInstructor
    fun deactivateInstructor(@PathVariable("id") id: String): ResponseEntity<Unit>
}