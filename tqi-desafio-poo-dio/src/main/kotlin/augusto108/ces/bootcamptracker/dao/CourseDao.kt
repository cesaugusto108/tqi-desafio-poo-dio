package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Course

interface CourseDao {
    fun saveCourse(course: Course): Course

    fun findAllCourses(): List<Course>

    fun findCourseById(id: Int): Course

    fun updateCourse(course: Course):Course

    fun deleteCourse(id: Int): Any
}