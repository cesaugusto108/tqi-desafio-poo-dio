package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.BootcampDTO
import augusto108.ces.bootcamptracker.model.entities.Bootcamp
import augusto108.ces.bootcamptracker.services.BootcampService
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
@RequestMapping("${API_VERSION}bootcamps")
class BootcampController(private val bootcampService: BootcampService) : BootcampOperations {
    override fun saveBootcamp(bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        val savedBootcamp: BootcampDTO = bootcampService.saveBootcamp(bootcamp)
        savedBootcamp.add(linkTo(BootcampController::class.java).slash("/${savedBootcamp.id}").withSelfRel())
        savedBootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBootcamp)
    }

    override fun findAllBootcamps(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<BootcampDTO>>> {
        val pagedModel: PagedModel<EntityModel<BootcampDTO>> = bootcampService.findAllBootcamps(page, max)
        val bootcampDTOList: MutableList<BootcampDTO?> = mutableListOf()

        for (entityModel: EntityModel<BootcampDTO> in pagedModel) bootcampDTOList.add(entityModel.content)

        for (bootcamp: BootcampDTO? in bootcampDTOList) {
            bootcamp?.add(linkTo(BootcampController::class.java).withSelfRel())
            bootcamp?.add(
                linkTo(BootcampController::class.java).slash("/${bootcamp.id}").withRel("bootcamp${bootcamp.id}")
            )
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pagedModel)
    }

    override fun findBootcampById(id: Int): ResponseEntity<BootcampDTO> {
        val bootcamp: BootcampDTO = bootcampService.findBootcampById(id)
        bootcamp.add(linkTo(BootcampController::class.java).slash("/${bootcamp.id}").withSelfRel())
        bootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(bootcamp)
    }

    override fun updateBootcamp(bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        val updatedBootcamp: BootcampDTO = bootcampService.updateBootcamp(bootcamp)
        updatedBootcamp.add(linkTo(BootcampController::class.java).slash("/${updatedBootcamp.id}").withSelfRel())
        updatedBootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedBootcamp)
    }

    override fun deleteBootcamp(id: Int): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(bootcampService.deleteBootcamp(id))
}