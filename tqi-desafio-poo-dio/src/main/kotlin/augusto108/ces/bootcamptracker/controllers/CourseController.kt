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
        courseService.saveCourse(course).also {
            it.add(linkTo(courseControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(courseControllerClass).withRel("all"))
            val uri: URI = it.getRequiredLink(IanaLinkRelations.SELF).toUri()
            return ResponseEntity.status(201).location(uri).body(it)
        }
    }

    override fun findAllCourses(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<CourseDTO>>> {
        courseService.findAllCourses(page, max).also {
            val courseDTOList: MutableList<CourseDTO?> = mutableListOf()
            for (entityModel: EntityModel<CourseDTO> in it) courseDTOList.add(entityModel.content)
            for (course: CourseDTO? in courseDTOList) {
                course?.add(linkTo(courseControllerClass).withSelfRel())
                course?.add(linkTo(courseControllerClass).slash("/${course.id}").withRel("course${course.id}"))
            }
            val headers = HttpHeaders()
            headers.add("X-Page-Number", it.metadata?.number.toString())
            headers.add("X-Page-Size", it.metadata?.size.toString())
            return ResponseEntity.status(200).headers(headers).body(it)
        }
    }

    override fun findCourseById(id: Int): ResponseEntity<CourseDTO> {
        courseService.findCourseById(id).also {
            it.add(linkTo(courseControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(courseControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
    }

    override fun updateCourse(course: Course): ResponseEntity<CourseDTO> {
        courseService.updateCourse(course).also {
            it.add(linkTo(courseControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(courseControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
    }

    override fun deleteCourse(id: Int): ResponseEntity<Unit> {
        courseService.deleteCourse(id)
        return ResponseEntity.status(204).build()
    }
}