package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.CourseDao
import augusto108.ces.bootcamptracker.model.Course
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CourseServiceImpl(private val courseDao: CourseDao) : CourseService {
    override fun saveCourse(course: Course): Course = courseDao.saveCourse(course)

    override fun findAllCourses(): List<Course> = courseDao.findAllCourses()

    override fun findCourseById(id: Int): Course = courseDao.findCourseById(id)

    override fun updateCourse(course: Course): Course = courseDao.updateCourse(course)

    override fun deleteCourse(id: Int): Any = courseDao.deleteCourse(id)
}