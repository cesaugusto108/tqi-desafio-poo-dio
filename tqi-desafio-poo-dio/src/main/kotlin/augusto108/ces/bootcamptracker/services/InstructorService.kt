package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor

interface InstructorService {
    fun saveInstructor(instructor: Instructor): InstructorDTO

    fun findAllInstructors(page: Int, max: Int): List<InstructorDTO>

    fun findInstructorById(id: Int): InstructorDTO

    fun instructorById(id: Int): Instructor

    fun updateInstructor(instructor: Instructor): InstructorDTO

    fun deleteInstructor(id: Int): Any
}