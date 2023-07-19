package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.controllers.annotations.mentoring.*
import augusto108.ces.bootcamptracker.dto.MentoringDTO
import augusto108.ces.bootcamptracker.entities.Mentoring
import augusto108.ces.bootcamptracker.services.MentoringService
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Mentoring", description = "endpoints to manage mentoring information")
@RestController
@RequestMapping("/v1/mentoring")
class MentoringController(private val mentoringService: MentoringService) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @SaveOperation
    fun saveMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val savedMentoring: MentoringDTO = mentoringService.saveMentoring(mentoring)
        savedMentoring.add(linkTo(MentoringController::class.java).slash("/${mentoring.id}").withSelfRel())
        savedMentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMentoring)
    }

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindAllOperation
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

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindByIdOperation
    fun findMentoringById(@PathVariable("id") id: Int): ResponseEntity<MentoringDTO> {
        val mentoring: MentoringDTO = mentoringService.findMentoringById(id)
        mentoring.add(linkTo(MentoringController::class.java).slash("/${mentoring.id}").withSelfRel())
        mentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(mentoring)
    }

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @UpdateOperation
    fun updateMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val updatedMentoring: MentoringDTO = mentoringService.updateMentoring(mentoring)
        updatedMentoring.add(linkTo(MentoringController::class.java).slash("/${updatedMentoring.id}").withSelfRel())
        updatedMentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedMentoring)
    }

    @DeleteMapping("/{id}")
    @DeleteOperation
    fun deleteMentoring(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(mentoringService.deleteMentoring(id))
}