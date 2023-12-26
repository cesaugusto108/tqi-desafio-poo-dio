package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.InstructorDTO
import augusto108.ces.bootcamptracker.model.entities.Instructor
import augusto108.ces.bootcamptracker.services.InstructorService
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.Link
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class InstructorController(private val instructorService: InstructorService) : InstructorOperations {

    val instructorControllerClass: Class<InstructorController> = InstructorController::class.java

    override fun saveInstructor(instructor: Instructor): ResponseEntity<InstructorDTO> {
        val savedInstructor: InstructorDTO = instructorService.saveInstructor(instructor)
        savedInstructor.add(linkTo(instructorControllerClass).slash("/${savedInstructor.id}").withSelfRel())
        savedInstructor.add(linkTo(instructorControllerClass).withRel("all"))
        val uri: URI = savedInstructor.getRequiredLink(IanaLinkRelations.SELF).toUri()
        return ResponseEntity.status(201).location(uri).body(savedInstructor)
    }

    override fun findAllInstructors(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<InstructorDTO>>> {
        val pagedModel: PagedModel<EntityModel<InstructorDTO>> = instructorService.findAllInstructors(page, max)
        val instructorDTOList: MutableList<InstructorDTO?> = mutableListOf()

        for (entityModel in pagedModel) instructorDTOList.add(entityModel.content)

        for (instructor: InstructorDTO? in instructorDTOList) {
            val selfLink: Link = linkTo(instructorControllerClass).withSelfRel()
            val instructorId = "/${instructor?.id}"
            val instructorRel = "instructor${instructor?.id}"
            val developerLink: Link = linkTo(instructorControllerClass).slash(instructorId).withRel(instructorRel)
            instructor?.add(selfLink)
            instructor?.add(developerLink)
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())
        return ResponseEntity.status(200).headers(headers).body(pagedModel)
    }

    override fun findInstructorById(id: String): ResponseEntity<InstructorDTO> {
        val instructor: InstructorDTO = instructorService.findInstructorById(id)
        instructor.add(linkTo(instructorControllerClass).slash("/${instructor.id}").withSelfRel())
        instructor.add(linkTo(instructorControllerClass).withRel("all"))
        return ResponseEntity.status(200).body(instructor)
    }

    override fun updateInstructor(instructor: Instructor): ResponseEntity<InstructorDTO> {
        val updatedInstructor: InstructorDTO = instructorService.updateInstructor(instructor)
        updatedInstructor.add(linkTo(instructorControllerClass).slash("/${updatedInstructor.id}").withSelfRel())
        updatedInstructor.add(linkTo(instructorControllerClass).withRel("all"))
        return ResponseEntity.status(200).body(updatedInstructor)
    }

    override fun deleteInstructor(id: String): ResponseEntity<Unit> {
        instructorService.deleteInstructor(id)
        return ResponseEntity.status(204).build()
    }

    override fun activateInstructor(id: String): ResponseEntity<Unit> {
        instructorService.activateInstructor(id)
        return ResponseEntity.status(204).build()
    }

    override fun deactivateInstructor(id: String): ResponseEntity<Unit> {
        instructorService.deactivateInstructor(id)
        return ResponseEntity.status(204).build()
    }
}