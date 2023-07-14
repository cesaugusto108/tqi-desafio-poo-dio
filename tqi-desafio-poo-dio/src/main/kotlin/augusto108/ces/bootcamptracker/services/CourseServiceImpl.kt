package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.CourseDao
import augusto108.ces.bootcamptracker.dto.CourseDTO
import augusto108.ces.bootcamptracker.dto.mapper.DTOMapper
import augusto108.ces.bootcamptracker.entities.Course
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CourseServiceImpl(private val courseDao: CourseDao) : CourseService {
    override fun saveCourse(course: Course): CourseDTO =
        DTOMapper.mapper().map(courseDao.saveCourse(course), CourseDTO::class.java)

    override fun findAllCourses(page: Int, max: Int): List<CourseDTO> {
        val courseDTOList: MutableList<CourseDTO> = ArrayList()

        for (course in courseDao.findAllCourses(page, max)) {
            courseDTOList.add(DTOMapper.mapper().map(course, CourseDTO::class.java))
        }

        return courseDTOList
    }

    override fun findCourseById(id: Int): CourseDTO = try {
        DTOMapper.mapper().map(courseDao.findCourseById(id), CourseDTO::class.java)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultForQueryException("Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateCourse(course: Course): CourseDTO =
        DTOMapper.mapper().map(courseDao.updateCourse(course), CourseDTO::class.java)

    override fun deleteCourse(id: Int): Any = courseDao.deleteCourse(id)
}