package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.InstructorDao
import augusto108.ces.bootcamptracker.dto.InstructorDTO
import augusto108.ces.bootcamptracker.dto.mapper.DTOMapper
import augusto108.ces.bootcamptracker.entities.Instructor
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class InstructorServiceImpl(private val instructorDao: InstructorDao) : InstructorService {
    override fun saveInstructor(instructor: Instructor): InstructorDTO = instructorDao.saveInstructor(instructor).map()

    override fun findAllInstructors(page: Int, max: Int): List<InstructorDTO> {
        val instructorDTOList: MutableList<InstructorDTO> = ArrayList()

        instructorDao.findAllInstructors(page, max).forEach { instructorDTOList.add(it.map()) }

        return instructorDTOList
    }

    override fun findInstructorById(id: Int): InstructorDTO = instructorById(id).map()

    override fun instructorById(id: Int): Instructor =
        try {
            instructorDao.findInstructorById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        } catch (e: NumberFormatException) {
            throw NumberFormatException()
        }

    override fun updateInstructor(instructor: Instructor): InstructorDTO =
        instructorDao.updateInstructor(instructor).map()

    override fun deleteInstructor(id: Int): Any = instructorDao.deleteInstructor(id)

    private fun Instructor.map() = DTOMapper.mapper().map(this, InstructorDTO::class.java)
}