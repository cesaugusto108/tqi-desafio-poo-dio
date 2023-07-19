package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.controllers.annotations.instructor.*
import augusto108.ces.bootcamptracker.dto.InstructorDTO
import augusto108.ces.bootcamptracker.entities.Instructor
import augusto108.ces.bootcamptracker.services.InstructorService
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Instructors", description = "endpoints to manage instructors information")
@RestController
@RequestMapping("/v1/instructors")
class InstructorController(private val instructorService: InstructorService) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @SaveOperation
    fun saveInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO> {
        val savedInstructor: InstructorDTO = instructorService.saveInstructor(instructor)
        savedInstructor.add(linkTo(InstructorController::class.java).slash("/${savedInstructor.id}").withSelfRel())
        savedInstructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedInstructor)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindAllOperation
    fun findAllInstructors(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<InstructorDTO>> {
        val instructorDTOList: List<InstructorDTO> = instructorService.findAllInstructors(page, max)

        for (instructor in instructorDTOList) {
            instructor.add(linkTo(InstructorController::class.java).withSelfRel())
            instructor.add(
                linkTo(InstructorController::class.java).slash("/${instructor.id}")
                    .withRel("instructor${instructor.id}")
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(instructorDTOList)
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindByIdOperation
    fun findInstructorById(@PathVariable("id") id: Int): ResponseEntity<InstructorDTO> {
        val instructor: InstructorDTO = instructorService.findInstructorById(id)
        instructor.add(linkTo(InstructorController::class.java).slash("/${instructor.id}").withSelfRel())
        instructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(instructor)
    }

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @UpdateOperation
    fun updateInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO> {
        val updatedInstructor: InstructorDTO = instructorService.updateInstructor(instructor)
        updatedInstructor.add(linkTo(InstructorController::class.java).slash("/${updatedInstructor.id}").withSelfRel())
        updatedInstructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedInstructor)
    }

    @DeleteMapping("/{id}")
    @DeleteOperation
    fun deleteInstructor(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(instructorService.deleteInstructor(id))
}