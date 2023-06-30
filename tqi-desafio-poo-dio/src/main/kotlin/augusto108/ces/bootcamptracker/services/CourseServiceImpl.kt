package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.CourseDao
import augusto108.ces.bootcamptracker.model.Course
import jakarta.persistence.NoResultException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CourseServiceImpl(private val courseDao: CourseDao) : CourseService {
    override fun saveCourse(course: Course): Course = courseDao.saveCourse(course)

    override fun findAllCourses(page: Int, max: Int): List<Course> = courseDao.findAllCourses(page, max)

    override fun findCourseById(id: Int): Course = try {
        courseDao.findCourseById(id)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultException("No result for query. Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateCourse(course: Course): Course = courseDao.updateCourse(course)

    override fun deleteCourse(id: Int): Any = courseDao.deleteCourse(id)
}