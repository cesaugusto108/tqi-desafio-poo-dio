package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.CourseDTO
import augusto108.ces.bootcamptracker.model.entities.Course
import augusto108.ces.bootcamptracker.services.CourseService
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class CourseController(private val courseService: CourseService) : CourseOperations {

    val courseControllerClass: Class<CourseController> = CourseController::class.java

    override fun saveCourse(course: Course): ResponseEntity<CourseDTO> {
        val savedCourse: CourseDTO = courseService.saveCourse(course)
        savedCourse.add(linkTo(courseControllerClass).slash("/${savedCourse.id}").withSelfRel())
        savedCourse.add(linkTo(courseControllerClass).withRel("all"))
        val uri: URI = savedCourse.getRequiredLink(IanaLinkRelations.SELF).toUri()
        return ResponseEntity.status(201).location(uri).body(savedCourse)
    }

    override fun findAllCourses(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<CourseDTO>>> {
        val pagedModel: PagedModel<EntityModel<CourseDTO>> = courseService.findAllCourses(page, max)
        val courseDTOList: MutableList<CourseDTO?> = mutableListOf()

        for (entityModel: EntityModel<CourseDTO> in pagedModel) courseDTOList.add(entityModel.content)

        for (course: CourseDTO? in courseDTOList) {
            course?.add(linkTo(courseControllerClass).withSelfRel())
            course?.add(linkTo(courseControllerClass).slash("/${course.id}").withRel("course${course.id}"))
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())
        return ResponseEntity.status(200).headers(headers).body(pagedModel)
    }

    override fun findCourseById(id: Int): ResponseEntity<CourseDTO> {
        val course: CourseDTO = courseService.findCourseById(id)
        course.add(linkTo(courseControllerClass).slash("/${course.id}").withSelfRel())
        course.add(linkTo(courseControllerClass).withRel("all"))
        return ResponseEntity.status(200).body(course)
    }

    override fun updateCourse(course: Course): ResponseEntity<CourseDTO> {
        val updatedCourse: CourseDTO = courseService.updateCourse(course)
        updatedCourse.add(linkTo(courseControllerClass).slash("/${updatedCourse.id}").withSelfRel())
        updatedCourse.add(linkTo(courseControllerClass).withRel("all"))
        return ResponseEntity.status(200).body(updatedCourse)
    }

    override fun deleteCourse(id: Int): ResponseEntity<Unit> {
        courseService.deleteCourse(id)
        return ResponseEntity.status(204).build()
    }
}