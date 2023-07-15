package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.BootcampDTO
import augusto108.ces.bootcamptracker.entities.Bootcamp
import augusto108.ces.bootcamptracker.services.BootcampService
import augusto108.ces.bootcamptracker.util.MediaType
import org.springframework.beans.BeanUtils
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bootcamps")
class BootcampController(private val bootcampService: BootcampService) : BeanUtils() {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun saveBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        val savedBootcamp: BootcampDTO = bootcampService.saveBootcamp(bootcamp)
        savedBootcamp.add(linkTo(BootcampController::class.java).slash("/${savedBootcamp.id}").withSelfRel())
        savedBootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedBootcamp)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllBootcamps(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<BootcampDTO>> {
        val bootcampDTOList: List<BootcampDTO> = bootcampService.findAllBootcamps(page, max)

        for (bootcamp in bootcampDTOList) {
            bootcamp.add(linkTo(BootcampController::class.java).withSelfRel())
            bootcamp.add(
                linkTo(BootcampController::class.java).slash("/${bootcamp.id}").withRel("bootcamp${bootcamp.id}")
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(bootcampDTOList)
    }

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findBootcampById(@PathVariable("id") id: Int): ResponseEntity<BootcampDTO> {
        val bootcamp: BootcampDTO = bootcampService.findBootcampById(id)
        bootcamp.add(linkTo(BootcampController::class.java).slash("/${bootcamp.id}").withSelfRel())
        bootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(bootcamp)
    }

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        val b: Bootcamp = bootcampService.bootcampById(bootcamp.id)

        copyProperties(bootcamp, b)

        val updatedBootcamp: BootcampDTO = bootcampService.updateBootcamp(b)

        updatedBootcamp.add(linkTo(BootcampController::class.java).slash("/${updatedBootcamp.id}").withSelfRel())
        updatedBootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedBootcamp)
    }

    @DeleteMapping("/{id}")
    fun deleteBootcamp(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(bootcampService.deleteBootcamp(id))
}