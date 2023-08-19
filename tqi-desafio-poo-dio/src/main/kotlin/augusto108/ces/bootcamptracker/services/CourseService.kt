package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.dto.CourseDTO
import augusto108.ces.bootcamptracker.model.entities.Course
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel

interface CourseService {
    fun saveCourse(course: Course): CourseDTO

    fun findAllCourses(page: Int, max: Int): PagedModel<EntityModel<CourseDTO>>

    fun findCourseById(id: Int): CourseDTO

    fun courseById(id: Int): Course

    fun updateCourse(course: Course): CourseDTO

    fun deleteCourse(id: Int)
}