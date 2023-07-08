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
    override fun saveInstructor(instructor: Instructor): InstructorDTO =
        DTOMapper.mapper().map(instructorDao.saveInstructor(instructor), InstructorDTO::class.java)

    override fun findAllInstructors(page: Int, max: Int): List<InstructorDTO> {
        val instructorDTOList: MutableList<InstructorDTO> = ArrayList()

        for (instructor in instructorDao.findAllInstructors(page, max)) {
            instructorDTOList.add(DTOMapper.mapper().map(instructor, InstructorDTO::class.java))
        }

        return instructorDTOList
    }

    override fun findInstructorById(id: Int): InstructorDTO =
        DTOMapper.mapper().map(instructorById(id), InstructorDTO::class.java)

    override fun instructorById(id: Int): Instructor = try {
        instructorDao.findInstructorById(id)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultForQueryException("Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateInstructor(instructor: Instructor): InstructorDTO =
        DTOMapper.mapper().map(instructorDao.updateInstructor(instructor), InstructorDTO::class.java)

    override fun deleteInstructor(id: Int): Any = instructorDao.deleteInstructor(id)
}