package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.InstructorDao
import augusto108.ces.bootcamptracker.model.Instructor
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InstructorServiceImpl(private val instructorDao: InstructorDao) : InstructorService {
    override fun saveInstructor(instructor: Instructor): Instructor = instructorDao.saveInstructor(instructor)

    override fun findAllInstructors(): List<Instructor> = instructorDao.findAllInstructors()

    override fun findInstructorById(id: Int): Instructor = instructorDao.findInstructorById(id)

    override fun updateInstructor(instructor: Instructor): Instructor = instructorDao.updateInstructor(instructor)

    override fun deleteInstructor(id: Int): Any = instructorDao.deleteInstructor(id)
}