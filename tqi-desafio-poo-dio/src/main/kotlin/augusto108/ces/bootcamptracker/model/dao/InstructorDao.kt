package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Instructor
import java.util.*

interface InstructorDao {
    fun saveInstructor(instructor: Instructor): Instructor

    fun findAllInstructors(): List<Instructor>

    fun findInstructorById(id: UUID): Instructor

    fun updateInstructor(instructor: Instructor): Instructor

    fun deleteInstructor(id: UUID)

    fun activateInstructor(id: UUID)

    fun deactivateInstructor(id: UUID)
}