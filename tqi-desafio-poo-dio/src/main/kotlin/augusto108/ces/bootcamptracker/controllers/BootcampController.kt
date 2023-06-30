package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.Bootcamp
import augusto108.ces.bootcamptracker.services.BootcampService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bootcamps")
class BootcampController(private val bootcampService: BootcampService) {
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun saveBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<Bootcamp> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(bootcampService.saveBootcamp(bootcamp))

    @GetMapping(produces = ["application/json"])
    fun findAllBootcamps(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<Bootcamp>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(bootcampService.findAllBootcamps(page, max))

    @GetMapping("/{id}", produces = ["application/json"])
    fun findBootcampById(@PathVariable("id") id: Int): ResponseEntity<Bootcamp> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(bootcampService.findBootcampById(id))

    @PutMapping(consumes = ["application/json"], produces = ["application/json"])
    fun updateBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<Bootcamp> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(bootcampService.updateBootcamp(bootcamp))

    @DeleteMapping("/{id}")
    fun deleteBootcamp(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(bootcampService.deleteBootcamp(id))
}