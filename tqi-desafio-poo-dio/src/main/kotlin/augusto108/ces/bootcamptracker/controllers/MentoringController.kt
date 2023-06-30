package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.Mentoring
import augusto108.ces.bootcamptracker.services.MentoringService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mentoring")
class MentoringController(private val mentoringService: MentoringService) {
    @PostMapping(consumes = ["application/json"], produces = ["application/json"])
    fun saveMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<Mentoring> =
        ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mentoringService.saveMentoring(mentoring))

    @GetMapping(produces = ["application/json"])
    fun findAllMentoring(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<List<Mentoring>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mentoringService.findAllMentoring(page, max))

    @GetMapping("/{id}", produces = ["application/json"])
    fun findMentoringById(@PathVariable("id") id: Int): ResponseEntity<Mentoring> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mentoringService.findMentoringById(id))

    @PutMapping(consumes = ["application/json"], produces = ["application/json"])
    fun updateMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<Mentoring> =
        ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(mentoringService.updateMentoring(mentoring))

    @DeleteMapping("/{id}")
    fun deleteMentoring(@PathVariable("id") id: Int): ResponseEntity<Any> =
        ResponseEntity
            .status(HttpStatus.NO_CONTENT)
            .body(mentoringService.deleteMentoring(id))
}