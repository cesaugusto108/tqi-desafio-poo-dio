package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.Instructor
import augusto108.ces.bootcamptracker.services.InstructorService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/instructors")
class InstructorController(private val instructorService: InstructorService) {
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun saveInstructor(@RequestBody instructor: Instructor): ResponseEntity<Instructor> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(instructorService.saveInstructor(instructor))

    @GetMapping(produces = ["application/json"])
    fun findAllInstructors(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<Instructor>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(instructorService.findAllInstructors(page, max))

    @GetMapping("/{id}", produces = ["application/json"])
    fun findInstructorById(@PathVariable("id") id: Int): ResponseEntity<Instructor> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(instructorService.findInstructorById(id))

    @PutMapping(consumes = ["application/json"], produces = ["application/json"])
    fun updateInstructor(@RequestBody instructor: Instructor): ResponseEntity<Instructor> {
        val i: Instructor = instructorService.findInstructorById(instructor.id)
        i.name = instructor.name
        i.age = instructor.age
        i.email = instructor.email
        i.level = instructor.level

        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(instructorService.updateInstructor(i))
    }

    @DeleteMapping("/{id}")
    fun deleteInstructor(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(instructorService.deleteInstructor(id))
}