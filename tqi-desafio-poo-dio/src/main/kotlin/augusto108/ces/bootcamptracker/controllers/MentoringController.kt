package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.dto.MentoringDTO
import augusto108.ces.bootcamptracker.entities.Mentoring
import augusto108.ces.bootcamptracker.services.MentoringService
import augusto108.ces.bootcamptracker.util.MediaType
import org.springframework.beans.BeanUtils
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mentoring")
class MentoringController(private val mentoringService: MentoringService) : BeanUtils() {
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

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
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
    fun updateMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val m: Mentoring = mentoringService.mentoringById(mentoring.id)

        copyProperties(mentoring, m)

        val updatedMentoring: MentoringDTO = mentoringService.updateMentoring(m)
        updatedMentoring.add(linkTo(MentoringController::class.java).slash("/${updatedMentoring.id}").withSelfRel())
        updatedMentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedMentoring)
    }

    @DeleteMapping("/{id}")
    fun deleteMentoring(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(mentoringService.deleteMentoring(id))
}