package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.CourseDTO
import augusto108.ces.bootcamptracker.entities.Course
import augusto108.ces.bootcamptracker.services.CourseService
import augusto108.ces.bootcamptracker.util.MediaType
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
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
    fun saveCourse(@RequestBody course: Course): ResponseEntity<CourseDTO> {
        val savedCourse: CourseDTO = courseService.saveCourse(course)
        savedCourse.add(linkTo(CourseController::class.java).slash("/${savedCourse.id}").withSelfRel())
        savedCourse.add(linkTo(CourseController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllCourses(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<CourseDTO>> {
        val courseDTOList: List<CourseDTO> = courseService.findAllCourses(page, max)

        for (course in courseDTOList) {
            course.add(linkTo(CourseController::class.java).withSelfRel())
            course.add(linkTo(CourseController::class.java).slash("/${course.id}").withRel("course${course.id}"))
        }

        return ResponseEntity.status(HttpStatus.OK).body(courseDTOList)
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findCourseById(@PathVariable("id") id: Int): ResponseEntity<CourseDTO> {
        val course: CourseDTO = courseService.findCourseById(id)
        course.add(linkTo(CourseController::class.java).slash("/${course.id}").withSelfRel())
        course.add(linkTo(CourseController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(course)
    }

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateCourse(@RequestBody course: Course): ResponseEntity<CourseDTO> {
        val updatedCourse: CourseDTO = courseService.updateCourse(course)
        updatedCourse.add(linkTo(CourseController::class.java).slash("/${updatedCourse.id}").withSelfRel())
        updatedCourse.add(linkTo(CourseController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedCourse)
    }

    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(courseService.deleteCourse(id))
}