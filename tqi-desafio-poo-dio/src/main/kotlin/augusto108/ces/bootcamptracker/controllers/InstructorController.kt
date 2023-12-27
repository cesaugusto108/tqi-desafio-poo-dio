package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.exceptions.UnmatchedIdException
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
        instructorService.saveInstructor(instructor).also {
            it.add(linkTo(instructorControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(instructorControllerClass).withRel("all"))
            val uri: URI = it.getRequiredLink(IanaLinkRelations.SELF).toUri()
            return ResponseEntity.status(201).location(uri).body(it)
        }
    }

    override fun findAllInstructors(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<InstructorDTO>>> {
        instructorService.findAllInstructors(page, max).also {
            val instructorDTOList: MutableList<InstructorDTO?> = mutableListOf()
            for (entityModel in it) instructorDTOList.add(entityModel.content)
            for (instructor: InstructorDTO? in instructorDTOList) {
                val selfLink: Link = linkTo(instructorControllerClass).withSelfRel()
                val instructorId = "/${instructor?.id}"
                val instructorRel = "instructor${instructor?.id}"
                val developerLink: Link = linkTo(instructorControllerClass).slash(instructorId).withRel(instructorRel)
                instructor?.add(selfLink)
                instructor?.add(developerLink)
            }
            val headers = HttpHeaders()
            headers.add("X-Page-Number", it.metadata?.number.toString())
            headers.add("X-Page-Size", it.metadata?.size.toString())
            return ResponseEntity.status(200).headers(headers).body(it)
        }
    }

    override fun findInstructorById(id: String): ResponseEntity<InstructorDTO> {
        instructorService.findInstructorById(id).also {
            it.add(linkTo(instructorControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(instructorControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
    }

    override fun updateInstructor(id: String, instructor: Instructor): ResponseEntity<InstructorDTO> {
        if (id == instructor.id.toString()) instructorService.updateInstructor(instructor).also {
            it.add(linkTo(instructorControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(instructorControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
        else throw UnmatchedIdException("Path id and request body id do not match")
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