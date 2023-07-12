package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.entities.Course
import augusto108.ces.bootcamptracker.services.CourseService
import augusto108.ces.bootcamptracker.util.MediaType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/courses")
class CourseController(private val courseService: CourseService) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun saveCourse(@RequestBody course: Course): ResponseEntity<Course> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(courseService.saveCourse(course))

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllCourses(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<Course>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.findAllCourses(page, max))

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findCourseById(@PathVariable("id") id: Int): ResponseEntity<Course> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.findCourseById(id))

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateCourse(@RequestBody course: Course): ResponseEntity<Course> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(courseService.updateCourse(course))

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(courseService.deleteCourse(id))
}