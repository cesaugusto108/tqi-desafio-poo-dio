package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.course.*
import augusto108.ces.bootcamptracker.model.dto.CourseDTO
import augusto108.ces.bootcamptracker.model.entities.Course
import augusto108.ces.bootcamptracker.util.API_VERSION
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Courses", description = "endpoints to manage courses information")
@RequestMapping("${API_VERSION}courses")
interface CourseOperations {

    @ResponseStatus(HttpStatus.CREATED)
    @SaveOperation
    @PostMapping(consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun saveCourse(@RequestBody course: Course): ResponseEntity<CourseDTO>

    @ResponseStatus(HttpStatus.OK)
    @FindAllOperation
    @GetMapping(produces = [MediaType.JSON, MediaType.YAML])
    fun findAllCourses(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<PagedModel<EntityModel<CourseDTO>>>

    @ResponseStatus(HttpStatus.OK)
    @FindByIdOperation
    @GetMapping("/{id}", produces = [MediaType.JSON, MediaType.YAML])
    fun findCourseById(@PathVariable("id") id: Int): ResponseEntity<CourseDTO>

    @ResponseStatus(HttpStatus.OK)
    @UpdateOperation
    @PutMapping("/{id}", consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun updateCourse(@PathVariable("id") id: Int, @RequestBody course: Course): ResponseEntity<CourseDTO>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteOperation
    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable("id") id: Int): ResponseEntity<Unit>
}