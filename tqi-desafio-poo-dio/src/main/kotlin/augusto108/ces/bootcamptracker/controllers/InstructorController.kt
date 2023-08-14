package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.services.InstructorService
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${API_VERSION}instructors")
class InstructorController(private val instructorService: InstructorService) : InstructorOperations {
    override fun saveInstructor(instructor: Instructor): ResponseEntity<InstructorDTO> {
        val savedInstructor: InstructorDTO = instructorService.saveInstructor(instructor)
        savedInstructor.add(linkTo(InstructorController::class.java).slash("/${savedInstructor.id}").withSelfRel())
        savedInstructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedInstructor)
    }

    override fun findAllInstructors(page: Int, max: Int): ResponseEntity<List<InstructorDTO>> {
        val instructorDTOList: List<InstructorDTO> = instructorService.findAllInstructors(page, max)

        for (instructor in instructorDTOList) {
            instructor.add(linkTo(InstructorController::class.java).withSelfRel())
            instructor.add(
                linkTo(InstructorController::class.java).slash("/${instructor.id}")
                    .withRel("instructor${instructor.id}")
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(instructorDTOList)
    }

    override fun findInstructorById(id: Int): ResponseEntity<InstructorDTO> {
        val instructor: InstructorDTO = instructorService.findInstructorById(id)
        instructor.add(linkTo(InstructorController::class.java).slash("/${instructor.id}").withSelfRel())
        instructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(instructor)
    }

    override fun updateInstructor(instructor: Instructor): ResponseEntity<InstructorDTO> {
        val updatedInstructor: InstructorDTO = instructorService.updateInstructor(instructor)
        updatedInstructor.add(linkTo(InstructorController::class.java).slash("/${updatedInstructor.id}").withSelfRel())
        updatedInstructor.add(linkTo(InstructorController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedInstructor)
    }

    override fun deleteInstructor(id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(instructorService.deleteInstructor(id))
}