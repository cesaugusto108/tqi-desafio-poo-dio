package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.dto.InstructorDTO
import augusto108.ces.bootcamptracker.entities.Instructor
import augusto108.ces.bootcamptracker.services.InstructorService
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Instructors", description = "endpoints to manage instructors information")
@RestController
@RequestMapping("/v1/instructors")
class InstructorController(private val instructorService: InstructorService) {

    @Operation(
        summary = "persists an instructor",
        responses = [
            ApiResponse(
                description = "Created",
                responseCode = "201",
                content = [Content(schema = Schema(implementation = InstructorDTO::class))]
            )
        ]
    )
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun saveInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO> {
        val savedInstructor: InstructorDTO = instructorService.saveInstructor(instructor)
        savedInstructor.add(linkTo(InstructorController::class.java).slash("/${savedInstructor.id}").withSelfRel())
        savedInstructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedInstructor)
    }

    @Operation(
        summary = "gets all instructors",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(array = ArraySchema(schema = Schema(implementation = InstructorDTO::class)))]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
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

    @Operation(
        summary = "get an instructor by id",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = InstructorDTO::class))]
            )
        ]
    )
    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findInstructorById(@PathVariable("id") id: Int): ResponseEntity<InstructorDTO> {
        val instructor: InstructorDTO = instructorService.findInstructorById(id)
        instructor.add(linkTo(InstructorController::class.java).slash("/${instructor.id}").withSelfRel())
        instructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(instructor)
    }

    @Operation(
        summary = "updates instructor information",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = InstructorDTO::class))]
            )
        ]
    )
    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO> {
        val updatedInstructor: InstructorDTO = instructorService.updateInstructor(instructor)
        updatedInstructor.add(linkTo(InstructorController::class.java).slash("/${updatedInstructor.id}").withSelfRel())
        updatedInstructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedInstructor)
    }

    @Operation(
        summary = "deletes an instructor by id",
        responses = [
            ApiResponse(
                description = "No content",
                responseCode = "204",
                content = [Content(schema = Schema(implementation = InstructorDTO::class))]
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteInstructor(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(instructorService.deleteInstructor(id))
}