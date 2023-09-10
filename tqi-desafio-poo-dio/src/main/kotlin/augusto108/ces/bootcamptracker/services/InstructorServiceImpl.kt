package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.dao.InstructorDao
import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.model.mapper.personMap
import augusto108.ces.bootcamptracker.services.PageRequest.getPersonPageRequest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class InstructorServiceImpl(
    private val instructorDao: InstructorDao,
    private val pagedResourcesAssembler: PagedResourcesAssembler<InstructorDTO>
) : InstructorService {
    override fun saveInstructor(instructor: Instructor): InstructorDTO =
        instructorDao.saveInstructor(instructor).personMap(InstructorDTO::class.java)

    override fun findAllInstructors(page: Int, max: Int): PagedModel<EntityModel<InstructorDTO>> {
        val instructorDTOList: MutableList<InstructorDTO> = ArrayList()

        instructorDao.findAllInstructors().forEach { instructorDTOList.add(it.personMap(InstructorDTO::class.java)) }

        return pagedResourcesAssembler.toModel(getPersonPageRequest(page, max, instructorDTOList))
    }

    override fun findInstructorById(id: String): InstructorDTO = instructorById(id).personMap(InstructorDTO::class.java)

    override fun instructorById(id: String): Instructor =
        try {
            instructorDao.findInstructorById(UUID.fromString(id))
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        }

    override fun updateInstructor(instructor: Instructor): InstructorDTO =
        instructorDao.updateInstructor(instructor).personMap(InstructorDTO::class.java)

    override fun deleteInstructor(id: String): Unit = instructorDao.deleteInstructor(UUID.fromString(id))

    override fun activateInstructor(id: String) {
        instructorDao.activateInstructor(UUID.fromString(id))
    }

    override fun deactivateInstructor(id: String) {
        instructorDao.deactivateInstructor(UUID.fromString(id))
    }
}