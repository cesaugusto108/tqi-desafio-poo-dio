package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel

interface InstructorService {
    fun saveInstructor(instructor: Instructor): InstructorDTO

    fun findAllInstructors(page: Int, max: Int): PagedModel<EntityModel<InstructorDTO>>

    fun findInstructorById(id: String): InstructorDTO

    fun instructorById(id: String): Instructor

    fun updateInstructor(instructor: Instructor): InstructorDTO

    fun deleteInstructor(id: String)

    fun activateInstructor(id: String)

    fun deactivateInstructor(id: String)
}