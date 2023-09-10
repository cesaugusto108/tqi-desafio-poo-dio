package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.services.InstructorService
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpHeaders
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

    override fun findAllInstructors(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<InstructorDTO>>> {
        val pagedModel: PagedModel<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max)
        val instructorDTOList: MutableList<InstructorDTO?> = mutableListOf()

        for (entityModel in pagedModel) instructorDTOList.add(entityModel.content)

        for (instructor: InstructorDTO? in instructorDTOList) {
            instructor?.add(linkTo(InstructorController::class.java).withSelfRel())
            instructor?.add(
                linkTo(InstructorController::class.java).slash("/${instructor.id}")
                    .withRel("instructor${instructor.id}")
            )
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pagedModel)
    }

    override fun findInstructorById(id: String): ResponseEntity<InstructorDTO> {
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

    override fun deleteInstructor(id: String): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(instructorService.deleteInstructor(id))

    override fun activateInstructor(id: String): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(instructorService.activateInstructor(id))
    }

    override fun deactivateInstructor(id: String): ResponseEntity<Unit> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(instructorService.deactivateInstructor(id))
    }
}