package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.entities.Bootcamp
import augusto108.ces.bootcamptracker.services.BootcampService
import augusto108.ces.bootcamptracker.util.MediaType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bootcamps")
class BootcampController(private val bootcampService: BootcampService) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun saveBootcamp(@RequestBody bootcamp: Bootcamp) =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(bootcampService.saveBootcamp(bootcamp))

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllBootcamps(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<Bootcamp>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(bootcampService.findAllBootcamps(page, max))

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findBootcampById(@PathVariable("id") id: Int): ResponseEntity<Bootcamp> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(bootcampService.findBootcampById(id))

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<Bootcamp> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(bootcampService.updateBootcamp(bootcamp))

    @DeleteMapping("/{id}")
    fun deleteBootcamp(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(bootcampService.deleteBootcamp(id))
}