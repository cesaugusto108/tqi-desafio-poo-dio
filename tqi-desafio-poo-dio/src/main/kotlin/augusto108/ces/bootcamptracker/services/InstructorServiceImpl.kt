package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.InstructorDao
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.Instructor
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InstructorServiceImpl(private val instructorDao: InstructorDao) : InstructorService {
    override fun saveInstructor(instructor: Instructor): Instructor = instructorDao.saveInstructor(instructor)

    override fun findAllInstructors(page: Int, max: Int): List<Instructor> = instructorDao.findAllInstructors(page, max)

    override fun findInstructorById(id: Int): Instructor = try {
        instructorDao.findInstructorById(id)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultForQueryException("Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateInstructor(instructor: Instructor): Instructor = instructorDao.updateInstructor(instructor)

    override fun deleteInstructor(id: Int): Any = instructorDao.deleteInstructor(id)
}