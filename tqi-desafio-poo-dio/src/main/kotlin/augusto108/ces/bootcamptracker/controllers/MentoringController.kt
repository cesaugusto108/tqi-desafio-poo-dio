package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.MentoringDTO
import augusto108.ces.bootcamptracker.entities.Mentoring
import augusto108.ces.bootcamptracker.services.MentoringService
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

@Tag(name = "Mentoring", description = "endpoints to manage mentoring information")
@RestController
@RequestMapping("/v1/mentoring")
class MentoringController(private val mentoringService: MentoringService) {

    @Operation(
        summary = "persists a mentoring",
        responses = [
            ApiResponse(
                description = "Created",
                responseCode = "201",
                content = [Content(schema = Schema(implementation = MentoringDTO::class))]
            )
        ]
    )
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun saveMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val savedMentoring: MentoringDTO = mentoringService.saveMentoring(mentoring)
        savedMentoring.add(linkTo(MentoringController::class.java).slash("/${mentoring.id}").withSelfRel())
        savedMentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMentoring)
    }

    @Operation(
        summary = "gets all mentoring",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(array = ArraySchema(schema = Schema(implementation = MentoringDTO::class)))]
            )
        ]
    )
    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllMentoring(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<MentoringDTO>> {
        val mentoringDTOList: List<MentoringDTO> = mentoringService.findAllMentoring(page, max)

        for (mentoring in mentoringDTOList) {
            mentoring.add(linkTo(MentoringController::class.java).withSelfRel())
            mentoring.add(
                linkTo(MentoringController::class.java).slash("/${mentoring.id}").withRel("mentoring${mentoring.id}")
            )
        }

        return ResponseEntity.status(HttpStatus.OK).body(mentoringDTOList)
    }

    @Operation(
        summary = "get mentoring by id",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = MentoringDTO::class))]
            )
        ]
    )
    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findMentoringById(@PathVariable("id") id: Int): ResponseEntity<MentoringDTO> {
        val mentoring: MentoringDTO = mentoringService.findMentoringById(id)
        mentoring.add(linkTo(MentoringController::class.java).slash("/${mentoring.id}").withSelfRel())
        mentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(mentoring)
    }

    @Operation(
        summary = "updates mentoring information",
        responses = [
            ApiResponse(
                description = "Success",
                responseCode = "200",
                content = [Content(schema = Schema(implementation = MentoringDTO::class))]
            )
        ]
    )
    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val updatedMentoring: MentoringDTO = mentoringService.updateMentoring(mentoring)
        updatedMentoring.add(linkTo(MentoringController::class.java).slash("/${updatedMentoring.id}").withSelfRel())
        updatedMentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedMentoring)
    }

    @Operation(
        summary = "deletes mentoring by id",
        responses = [
            ApiResponse(
                description = "No content",
                responseCode = "204",
                content = [Content(schema = Schema(implementation = MentoringDTO::class))]
            )
        ]
    )
    @DeleteMapping("/{id}")
    fun deleteMentoring(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(mentoringService.deleteMentoring(id))
}