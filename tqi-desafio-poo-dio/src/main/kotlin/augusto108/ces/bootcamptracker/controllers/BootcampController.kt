package augusto108.ces.bootcamptracker.controllers

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

    override fun saveBootcamp(bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        val savedBootcamp: BootcampDTO = bootcampService.saveBootcamp(bootcamp)
        savedBootcamp.add(linkTo(BootcampController::class.java).slash("/${savedBootcamp.id}").withSelfRel())
        savedBootcamp.add(linkTo(BootcampController::class.java).withRel("all"))
        val uri: URI = savedBootcamp.getRequiredLink(IanaLinkRelations.SELF).toUri()
        return ResponseEntity.status(201).location(uri).body(savedBootcamp)
    }

    override fun findAllBootcamps(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<BootcampDTO>>> {
        val pagedModel: PagedModel<EntityModel<BootcampDTO>> = bootcampService.findAllBootcamps(page, max)
        val bootcampDTOList: MutableList<BootcampDTO?> = mutableListOf()

        for (entityModel: EntityModel<BootcampDTO> in pagedModel) bootcampDTOList.add(entityModel.content)

        for (bootcamp: BootcampDTO? in bootcampDTOList) {
            val selfLink: Link = linkTo(BootcampController::class.java).withSelfRel()
            val bootcampId = bootcamp?.id
            val bootcampRel = "bootcamp${bootcampId}"
            val bootcampLink: Link = linkTo(BootcampController::class.java).slash(bootcampId).withRel(bootcampRel)
            bootcamp?.add(selfLink)
            bootcamp?.add(bootcampLink)
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())
        return ResponseEntity.status(200).headers(headers).body(pagedModel)
    }

    override fun findBootcampById(id: Int): ResponseEntity<BootcampDTO> {
        val bootcamp: BootcampDTO = bootcampService.findBootcampById(id)
        bootcamp.add(linkTo(BootcampController::class.java).slash("/${bootcamp.id}").withSelfRel())
        bootcamp.add(linkTo(BootcampController::class.java).withRel("all"))
        return ResponseEntity.status(200).body(bootcamp)
    }

    override fun updateBootcamp(bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        val updatedBootcamp: BootcampDTO = bootcampService.updateBootcamp(bootcamp)
        updatedBootcamp.add(linkTo(BootcampController::class.java).slash("/${updatedBootcamp.id}").withSelfRel())
        updatedBootcamp.add(linkTo(BootcampController::class.java).withRel("all"))
        return ResponseEntity.status(200).body(updatedBootcamp)
    }

    override fun deleteBootcamp(id: Int): ResponseEntity<Unit> {
        bootcampService.deleteBootcamp(id)
        return ResponseEntity.status(204).build()
    }
}