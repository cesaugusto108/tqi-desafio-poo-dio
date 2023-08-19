package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.CourseDTO
import augusto108.ces.bootcamptracker.model.entities.Course
import augusto108.ces.bootcamptracker.services.CourseService
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${API_VERSION}courses")
class CourseController(private val courseService: CourseService) : CourseOperations {
    override fun saveCourse(course: Course): ResponseEntity<CourseDTO> {
        val savedCourse: CourseDTO = courseService.saveCourse(course)
        savedCourse.add(linkTo(CourseController::class.java).slash("/${savedCourse.id}").withSelfRel())
        savedCourse.add(linkTo(CourseController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse)
    }

    override fun findAllCourses(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<CourseDTO>>> {
        val pagedModel: PagedModel<EntityModel<CourseDTO>> = courseService.findAllCourses(page, max)
        val courseDTOList: MutableList<CourseDTO?> = mutableListOf()

        for (entityModel: EntityModel<CourseDTO> in pagedModel) courseDTOList.add(entityModel.content)

        for (course: CourseDTO? in courseDTOList) {
            course?.add(linkTo(CourseController::class.java).withSelfRel())
            course?.add(linkTo(CourseController::class.java).slash("/${course.id}").withRel("course${course.id}"))
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pagedModel)
    }

    override fun findCourseById(id: Int): ResponseEntity<CourseDTO> {
        val course: CourseDTO = courseService.findCourseById(id)
        course.add(linkTo(CourseController::class.java).slash("/${course.id}").withSelfRel())
        course.add(linkTo(CourseController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(course)
    }

    override fun updateCourse(course: Course): ResponseEntity<CourseDTO> {
        val updatedCourse: CourseDTO = courseService.updateCourse(course)
        updatedCourse.add(linkTo(CourseController::class.java).slash("/${updatedCourse.id}").withSelfRel())
        updatedCourse.add(linkTo(CourseController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedCourse)
    }

    override fun deleteCourse(id: Int): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(courseService.deleteCourse(id))
}