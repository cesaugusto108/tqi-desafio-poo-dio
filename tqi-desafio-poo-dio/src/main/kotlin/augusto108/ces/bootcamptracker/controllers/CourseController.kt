package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.CourseDTO
import augusto108.ces.bootcamptracker.entities.Course
import augusto108.ces.bootcamptracker.services.CourseService
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

@Tag(name = "Courses", description = "endpoints to manage courses information")
@RestController
@RequestMapping("/v1/courses")
class CourseController(private val courseService: CourseService) {

    @Operation(
        summary = "persists a course",
        responses = [
            ApiResponse(
                description = "Created",
                responseCode = "201",
                content = [Content(schema = Schema(implementation = CourseDTO::class))]
            )
        ]
    )
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

    @Operation(
        summary = "gets all courses",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(array = ArraySchema(schema = Schema(implementation = CourseDTO::class)))]
            )
        ]
    )
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

    @Operation(
        summary = "get a course by id",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = CourseDTO::class))]
            )
        ]
    )
    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findCourseById(@PathVariable("id") id: Int): ResponseEntity<CourseDTO> {
        val course: CourseDTO = courseService.findCourseById(id)
        course.add(linkTo(CourseController::class.java).slash("/${course.id}").withSelfRel())
        course.add(linkTo(CourseController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(course)
    }

    @Operation(
        summary = "updates course information",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = CourseDTO::class))]
            )
        ]
    )
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

    @Operation(
        summary = "deletes a course by id",
        responses = [
            ApiResponse(
                description = "No content",
                responseCode = "204",
                content = [Content(schema = Schema(implementation = CourseDTO::class))]
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteCourse(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(courseService.deleteCourse(id))
}