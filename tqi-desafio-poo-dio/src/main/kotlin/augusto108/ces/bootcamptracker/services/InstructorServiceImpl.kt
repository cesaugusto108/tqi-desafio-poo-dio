package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.dao.InstructorDao
import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.model.mapper.map
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InstructorServiceImpl(private val instructorDao: InstructorDao) : InstructorService {
    override fun saveInstructor(instructor: Instructor): InstructorDTO =
        instructorDao.saveInstructor(instructor).map(InstructorDTO::class.java)

    override fun findAllInstructors(page: Int, max: Int): List<InstructorDTO> {
        val instructorDTOList: MutableList<InstructorDTO> = ArrayList()

        instructorDao.findAllInstructors(page, max).forEach { instructorDTOList.add(it.map(InstructorDTO::class.java)) }

        return instructorDTOList
    }

    override fun findInstructorById(id: Int): InstructorDTO = instructorById(id).map(InstructorDTO::class.java)

    override fun instructorById(id: Int): Instructor =
        try {
            instructorDao.findInstructorById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        } catch (e: NumberFormatException) {
            throw NumberFormatException()
        }

    override fun updateInstructor(instructor: Instructor): InstructorDTO =
        instructorDao.updateInstructor(instructor).map(InstructorDTO::class.java)

    override fun deleteInstructor(id: Int): Unit = instructorDao.deleteInstructor(id)

    override fun activateInstructor(id: Int) {
        instructorDao.activateInstructor(id)
    }

    override fun deactivateInstructor(id: Int) {
        instructorDao.deactivateInstructor(id)
    }
}