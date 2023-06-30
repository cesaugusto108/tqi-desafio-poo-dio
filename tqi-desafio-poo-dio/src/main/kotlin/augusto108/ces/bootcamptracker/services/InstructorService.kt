package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.Instructor

interface InstructorService {
    fun saveInstructor(instructor: Instructor): Instructor

    fun findAllInstructors(page: Int, max: Int): List<Instructor>

    fun findInstructorById(id: Int): Instructor

    fun updateInstructor(instructor: Instructor): Instructor

    fun deleteInstructor(id: Int): Any
}