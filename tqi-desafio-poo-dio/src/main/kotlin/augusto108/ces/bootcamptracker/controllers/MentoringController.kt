package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.MentoringDTO
import augusto108.ces.bootcamptracker.model.entities.Mentoring
import augusto108.ces.bootcamptracker.services.MentoringService
import augusto108.ces.bootcamptracker.util.API_VERSION
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${API_VERSION}mentoring")
class MentoringController(private val mentoringService: MentoringService) : MentoringOperations {
    override fun saveMentoring(mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val savedMentoring: MentoringDTO = mentoringService.saveMentoring(mentoring)
        savedMentoring.add(linkTo(MentoringController::class.java).slash("/${mentoring.id}").withSelfRel())
        savedMentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.CREATED).body(savedMentoring)
    }

    override fun findAllMentoring(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<MentoringDTO>>> {
        val pagedModel: PagedModel<EntityModel<MentoringDTO>> = mentoringService.findAllMentoring(page, max)
        val mentoringDTOList: MutableList<MentoringDTO?> = mutableListOf()

        for (entityModel: EntityModel<MentoringDTO> in pagedModel) mentoringDTOList.add(entityModel.content)

        for (mentoring: MentoringDTO? in mentoringDTOList) {
            mentoring?.add(linkTo(MentoringController::class.java).withSelfRel())
            mentoring?.add(
                linkTo(MentoringController::class.java).slash("/${mentoring.id}").withRel("mentoring${mentoring.id}")
            )
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(pagedModel)
    }

    override fun findMentoringById(id: Int): ResponseEntity<MentoringDTO> {
        val mentoring: MentoringDTO = mentoringService.findMentoringById(id)
        mentoring.add(linkTo(MentoringController::class.java).slash("/${mentoring.id}").withSelfRel())
        mentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(mentoring)
    }

    override fun updateMentoring(mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val updatedMentoring: MentoringDTO = mentoringService.updateMentoring(mentoring)
        updatedMentoring.add(linkTo(MentoringController::class.java).slash("/${updatedMentoring.id}").withSelfRel())
        updatedMentoring.add(linkTo(MentoringController::class.java).withRel("all"))

        return ResponseEntity.status(HttpStatus.OK).body(updatedMentoring)
    }

    override fun deleteMentoring(id: Int): ResponseEntity<Unit> =
        ResponseEntity.status(HttpStatus.NO_CONTENT).body(mentoringService.deleteMentoring(id))
}