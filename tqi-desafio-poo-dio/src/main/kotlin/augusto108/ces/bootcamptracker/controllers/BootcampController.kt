package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.BootcampDTO
import augusto108.ces.bootcamptracker.entities.Bootcamp
import augusto108.ces.bootcamptracker.services.BootcampService
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Bootcamps", description = "endpoints to manage bootcamps information")
@RestController
@RequestMapping("/v1/bootcamps")
class BootcampController(private val bootcampService: BootcampService) {

    @Operation(
        summary = "persists a bootcamp",
        responses = [
            ApiResponse(
                description = "Created",
                responseCode = "201",
                content = [Content(schema = Schema(implementation = BootcampDTO::class))]
            )
        ]
    )
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

    @Operation(
        summary = "gets all bootcamps",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(array = ArraySchema(schema = Schema(implementation = BootcampDTO::class)))]
            )
        ]
    )
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

    @Operation(
        summary = "get a bootcamp by id",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = BootcampDTO::class))]
            )
        ]
    )
    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findBootcampById(@PathVariable("id") id: Int): ResponseEntity<BootcampDTO> {
        val bootcamp: BootcampDTO = bootcampService.findBootcampById(id)
        bootcamp.add(linkTo(BootcampController::class.java).slash("/${bootcamp.id}").withSelfRel())
        bootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(bootcamp)
    }

    @Operation(
        summary = "updates bootcamp information",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = BootcampDTO::class))]
            )
        ]
    )
    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateBootcamp(@RequestBody bootcamp: Bootcamp): ResponseEntity<BootcampDTO> {
        val updatedBootcamp: BootcampDTO = bootcampService.updateBootcamp(bootcamp)
        updatedBootcamp.add(linkTo(BootcampController::class.java).slash("/${updatedBootcamp.id}").withSelfRel())
        updatedBootcamp.add(linkTo(BootcampController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedBootcamp)
    }

    @Operation(
        summary = "deletes a bootcamp by id",
        responses = [
            ApiResponse(
                description = "No content",
                responseCode = "204",
                content = [Content(schema = Schema(implementation = BootcampDTO::class))]
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteBootcamp(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(bootcampService.deleteBootcamp(id))
}