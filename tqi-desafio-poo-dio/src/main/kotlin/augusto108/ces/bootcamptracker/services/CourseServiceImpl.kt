package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.dao.CourseDao
import augusto108.ces.bootcamptracker.model.dto.CourseDTO
import augusto108.ces.bootcamptracker.model.entities.Course
import augusto108.ces.bootcamptracker.model.mapper.map
import augusto108.ces.bootcamptracker.services.PageRequest.getPageRequest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CourseServiceImpl(
    private val courseDao: CourseDao,
    private val pagedResourcesAssembler: PagedResourcesAssembler<CourseDTO>
) : CourseService {
    override fun saveCourse(course: Course): CourseDTO = courseDao.saveCourse(course).map(CourseDTO::class.java)

    override fun findAllCourses(page: Int, max: Int): PagedModel<EntityModel<CourseDTO>> {
        val courseDTOList: MutableList<CourseDTO> = ArrayList()

        courseDao.findAllCourses().forEach { courseDTOList.add(it.map(CourseDTO::class.java)) }

        return pagedResourcesAssembler.toModel(getPageRequest(page, max, courseDTOList))
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
        courseDao.updateCourse(course).map(CourseDTO::class.java)

    override fun deleteCourse(id: Int): Unit = courseDao.deleteCourse(id)
}