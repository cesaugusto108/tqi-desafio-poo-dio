package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.annotations.mentoring.*
import augusto108.ces.bootcamptracker.model.dto.MentoringDTO
import augusto108.ces.bootcamptracker.model.entities.Mentoring
import augusto108.ces.bootcamptracker.util.API_VERSION
import augusto108.ces.bootcamptracker.util.MediaType
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "Mentoring", description = "endpoints to manage mentoring information")
@RequestMapping("${API_VERSION}mentoring")
interface MentoringOperations {

    @ResponseStatus(HttpStatus.CREATED)
    @SaveOperation
    @PostMapping(consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun saveMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO>

    @ResponseStatus(HttpStatus.OK)
    @FindAllOperation
    @GetMapping(produces = [MediaType.JSON, MediaType.YAML])
    fun findAllMentoring(
        @RequestParam(defaultValue = "0", required = false) page: Int,
        @RequestParam(defaultValue = "10", required = false) max: Int
    ): ResponseEntity<PagedModel<EntityModel<MentoringDTO>>>

    @ResponseStatus(HttpStatus.OK)
    @FindByIdOperation
    @GetMapping("/{id}", produces = [MediaType.JSON, MediaType.YAML])
    fun findMentoringById(@PathVariable("id") id: Int): ResponseEntity<MentoringDTO>

    @ResponseStatus(HttpStatus.OK)
    @UpdateOperation
    @PutMapping(consumes = [MediaType.JSON, MediaType.YAML], produces = [MediaType.JSON, MediaType.YAML])
    fun updateMentoring(@RequestBody mentoring: Mentoring): ResponseEntity<MentoringDTO>

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteOperation
    @DeleteMapping("/{id}")
    fun deleteMentoring(@PathVariable("id") id: Int): ResponseEntity<Unit>
}