package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.entities.Mentoring
import augusto108.ces.bootcamptracker.services.MentoringService
import augusto108.ces.bootcamptracker.util.MediaType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mentoring")
class MentoringController(private val mentoringService: MentoringService) {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun saveMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<Mentoring> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mentoringService.saveMentoring(mentoring))

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findAllMentoring(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<Mentoring>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(mentoringService.findAllMentoring(page, max))

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    fun findMentoringById(@PathVariable("id") id: Int): ResponseEntity<Mentoring> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(mentoringService.findMentoringById(id))

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    fun updateMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<Mentoring> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(mentoringService.updateMentoring(mentoring))

    @DeleteMapping("/{id}")
    fun deleteMentoring(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(mentoringService.deleteMentoring(id))
}