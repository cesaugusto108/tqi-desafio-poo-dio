package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.exceptions.UnmatchedIdException
import augusto108.ces.bootcamptracker.model.dto.BootcampDTO
import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.services.BootcampService
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
class BootcampController(private val bootcampService: BootcampService) : BootcampOperations {

    val bootcampControllerClass: Class<BootcampController> = BootcampController::class.java

    override fun saveBootcamp(bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        bootcampService.saveBootcamp(bootcamp).also {
            it.add(linkTo(bootcampControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(bootcampControllerClass).withRel("all"))
            val uri: URI = it.getRequiredLink(IanaLinkRelations.SELF).toUri()
            return ResponseEntity.status(201).location(uri).body(it)
        }
    }

    override fun findAllBootcamps(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<BootcampDTO>>> {
        bootcampService.findAllBootcamps(page, max).also {
            val bootcampDTOList: MutableList<BootcampDTO?> = mutableListOf()
            for (entityModel: EntityModel<BootcampDTO> in it) bootcampDTOList.add(entityModel.content)
            for (bootcamp: BootcampDTO? in bootcampDTOList) {
                val selfLink: Link = linkTo(bootcampControllerClass).withSelfRel()
                val bootcampId: Int? = bootcamp?.id
                val bootcampRel = "bootcamp${bootcampId}"
                val bootcampLink: Link = linkTo(bootcampControllerClass).slash(bootcampId).withRel(bootcampRel)
                bootcamp?.add(selfLink)
                bootcamp?.add(bootcampLink)
            }
            val headers = HttpHeaders()
            headers.add("X-Page-Number", it.metadata?.number.toString())
            headers.add("X-Page-Size", it.metadata?.size.toString())
            return ResponseEntity.status(200).headers(headers).body(it)
        }
    }

    override fun findBootcampById(id: Int): ResponseEntity<BootcampDTO> {
        bootcampService.findBootcampById(id).also {
            it.add(linkTo(bootcampControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(bootcampControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
    }

    override fun updateBootcamp(id: Int, bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        if (id == bootcamp.id) bootcampService.updateBootcamp(bootcamp).also {
            it.add(linkTo(bootcampControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(bootcampControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
        else throw UnmatchedIdException("Path id and request body id do not match")
    }

    override fun deleteBootcamp(id: Int): ResponseEntity<Unit> {
        bootcampService.deleteBootcamp(id)
        return ResponseEntity.status(204).build()
    }
}