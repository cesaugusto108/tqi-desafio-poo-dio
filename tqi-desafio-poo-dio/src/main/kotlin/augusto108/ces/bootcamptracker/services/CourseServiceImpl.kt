package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.CourseDao
import augusto108.ces.bootcamptracker.dto.CourseDTO
import augusto108.ces.bootcamptracker.dto.mapper.map
import augusto108.ces.bootcamptracker.entities.Course
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CourseServiceImpl(private val courseDao: CourseDao) : CourseService {
    override fun saveCourse(course: Course): CourseDTO = courseDao.saveCourse(course).map(CourseDTO::class.java)

    override fun findAllCourses(page: Int, max: Int): List<CourseDTO> {
        val courseDTOList: MutableList<CourseDTO> = ArrayList()

        courseDao.findAllCourses(page, max).forEach { courseDTOList.add(it.map(CourseDTO::class.java)) }

        return courseDTOList
    }

    override fun findCourseById(id: Int): CourseDTO = courseById(id).map(CourseDTO::class.java)

    override fun courseById(id: Int): Course =
        try {
            courseDao.findCourseById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        } catch (e: NumberFormatException) {
            throw NumberFormatException()
        }

    override fun updateCourse(course: Course): CourseDTO =
        courseDao.updateCourse(course.copyProperties(course)).map(CourseDTO::class.java)

    override fun deleteCourse(id: Int): Any = courseDao.deleteCourse(id)
}