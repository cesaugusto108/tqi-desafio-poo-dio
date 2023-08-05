package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.controllers.annotations.bootcamp.*
import augusto108.ces.bootcamptracker.dto.BootcampDTO
import augusto108.ces.bootcamptracker.entities.Bootcamp
import augusto108.ces.bootcamptracker.services.BootcampService
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("${API_VERSION}bootcamps")
class BootcampController(private val bootcampService: BootcampService) : BootcampOperations {
    override fun saveBootcamp(bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        val savedBootcamp: BootcampDTO = bootcampService.saveBootcamp(bootcamp)
        savedBootcamp.add(linkTo(BootcampController::class.java).slash("/${savedBootcamp.id}").withSelfRel())
        savedBootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBootcamp)
    }

    override fun findAllBootcamps(page: Int, max: Int): ResponseEntity<List<BootcampDTO>> {
        val bootcampDTOList: List<BootcampDTO> = bootcampService.findAllBootcamps(page, max)

        for (bootcamp in bootcampDTOList) {
            bootcamp.add(linkTo(BootcampController::class.java).withSelfRel())
            bootcamp.add(
                linkTo(BootcampController::class.java).slash("/${bootcamp.id}").withRel("bootcamp${bootcamp.id}")
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(bootcampDTOList)
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

    override fun deleteBootcamp(id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(bootcampService.deleteBootcamp(id))
}