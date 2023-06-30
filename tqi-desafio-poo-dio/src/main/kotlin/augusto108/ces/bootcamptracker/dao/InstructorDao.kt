package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.model.Instructor

interface InstructorDao {
    fun saveInstructor(instructor: Instructor): Instructor

    fun findAllInstructors(page: Int, max: Int): List<Instructor>

    fun findInstructorById(id: Int): Instructor

    fun updateInstructor(instructor: Instructor): Instructor

    fun deleteInstructor(id: Int): Any
}