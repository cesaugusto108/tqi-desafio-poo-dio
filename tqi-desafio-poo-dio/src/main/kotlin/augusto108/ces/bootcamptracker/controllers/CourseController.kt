package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.Course
import augusto108.ces.bootcamptracker.services.CourseService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/courses")
class CourseController(private val courseService: CourseService) {
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun saveCourse(@RequestBody course: Course): ResponseEntity<Course> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseService.saveCourse(course))

    @GetMapping(produces = ["application/json"])
    fun findAllCourses(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<Course>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseService.findAllCourses(page, max))

    @GetMapping("/{id}", produces = ["application/json"])
    fun findCourseById(@PathVariable("id") id: Int): ResponseEntity<Course> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseService.findCourseById(id))

    @PutMapping(consumes = ["application/json"], produces = ["application/json"])
    fun updateCourse(@RequestBody course: Course): ResponseEntity<Course> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(courseService.updateCourse(course))

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(courseService.deleteCourse(id))
}