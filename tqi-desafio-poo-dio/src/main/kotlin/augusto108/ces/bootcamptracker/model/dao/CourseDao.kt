package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Course

interface CourseDao {

    fun saveCourse(course: Course): Course

    fun findAllCourses(): List<Course>

    fun findCourseById(id: Int): Course

    fun updateCourse(course: Course): Course

    fun deleteCourse(id: Int)
}