package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.course.*
import augusto108.ces.bootcamptracker.model.dto.CourseDTO
import augusto108.ces.bootcamptracker.model.entities.Course
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Courses", description = "endpoints to manage courses information")
interface CourseOperations {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @SaveOperation
    fun saveCourse(@RequestBody course: Course): ResponseEntity<CourseDTO>

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindAllOperation
    fun findAllCourses(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<CourseDTO>>

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindByIdOperation
    fun findCourseById(@PathVariable("id") id: Int): ResponseEntity<CourseDTO>

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @UpdateOperation
    fun updateCourse(@RequestBody course: Course): ResponseEntity<CourseDTO>

    @DeleteMapping("/{id}")
    @DeleteOperation
    fun deleteCourse(@PathVariable("id") id: Int): ResponseEntity<Any>
}