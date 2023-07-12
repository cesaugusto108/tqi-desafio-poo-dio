package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.InstructorDTO
import augusto108.ces.bootcamptracker.entities.Instructor
import augusto108.ces.bootcamptracker.services.InstructorService
import augusto108.ces.bootcamptracker.util.MediaType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/instructors")
class InstructorController(private val instructorService: InstructorService) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun saveInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(instructorService.saveInstructor(instructor))

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllInstructors(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<InstructorDTO>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(instructorService.findAllInstructors(page, max))

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findInstructorById(@PathVariable("id") id: Int): ResponseEntity<InstructorDTO> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(instructorService.findInstructorById(id))

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateInstructor(@RequestBody instructor: Instructor): ResponseEntity<InstructorDTO> {
        val i: Instructor = instructorService.instructorById(instructor.id)

        i.name = instructor.name
        i.age = instructor.age
        i.email = instructor.email
        i.username = instructor.username
        i.password = instructor.password
        i.level = instructor.level

        return ResponseEntity
            .status(HttpStatus.OK)
            .body(instructorService.updateInstructor(i))
    }

    @DeleteMapping("/{id}")
    fun deleteInstructor(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(instructorService.deleteInstructor(id))
}