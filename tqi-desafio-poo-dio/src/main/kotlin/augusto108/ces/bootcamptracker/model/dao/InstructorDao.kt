package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Instructor

interface InstructorDao {
    fun saveInstructor(instructor: Instructor): Instructor

    fun findAllInstructors(): List<Instructor>

    fun findInstructorById(id: Int): Instructor

    fun updateInstructor(instructor: Instructor): Instructor

    fun deleteInstructor(id: Int)

    fun activateInstructor(id: Int)

    fun deactivateInstructor(id: Int)
}