package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.mentoring.*
import augusto108.ces.bootcamptracker.model.dto.MentoringDTO
import augusto108.ces.bootcamptracker.model.entities.Mentoring
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Mentoring", description = "endpoints to manage mentoring information")
interface MentoringOperations {
    @PostMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @SaveOperation
    fun saveMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO>

    @GetMapping(produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindAllOperation
    fun findAllMentoring(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<PagedModel<EntityModel<MentoringDTO>>>

    @GetMapping("/{id}", produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML])
    @FindByIdOperation
    fun findMentoringById(@PathVariable("id") id: Int): ResponseEntity<MentoringDTO>

    @PutMapping(
        consumes = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML],
        produces = [MediaType.APPLICATION_JSON, MediaType.APPLICATION_YAML]
    )
    @UpdateOperation
    fun updateMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO>

    @DeleteMapping("/{id}")
    @DeleteOperation
    fun deleteMentoring(@PathVariable("id") id: Int): ResponseEntity<Unit>
}