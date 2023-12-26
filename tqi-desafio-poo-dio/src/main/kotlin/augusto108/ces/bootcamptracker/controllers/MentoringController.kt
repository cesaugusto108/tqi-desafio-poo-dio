package augusto108.ces.bootcamptracker.controllers

import augusto108.ces.bootcamptracker.model.dto.MentoringDTO
import augusto108.ces.bootcamptracker.model.entities.Mentoring
import augusto108.ces.bootcamptracker.services.MentoringService
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.IanaLinkRelations
import org.springframework.hateoas.Link
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
class MentoringController(private val mentoringService: MentoringService) : MentoringOperations {

    val mentoringControllerClass: Class<MentoringController> = MentoringController::class.java

    override fun saveMentoring(mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val savedMentoring: MentoringDTO = mentoringService.saveMentoring(mentoring)
        savedMentoring.add(linkTo(mentoringControllerClass).slash("/${mentoring.id}").withSelfRel())
        savedMentoring.add(linkTo(mentoringControllerClass).withRel("all"))
        val uri: URI = savedMentoring.getRequiredLink(IanaLinkRelations.SELF).toUri()
        return ResponseEntity.status(201).location(uri).body(savedMentoring)
    }

    override fun findAllMentoring(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<MentoringDTO>>> {
        val pagedModel: PagedModel<EntityModel<MentoringDTO>> = mentoringService.findAllMentoring(page, max)
        val mentoringDTOList: MutableList<MentoringDTO?> = mutableListOf()

        for (entityModel: EntityModel<MentoringDTO> in pagedModel) mentoringDTOList.add(entityModel.content)

        for (mentoring: MentoringDTO? in mentoringDTOList) {
            val selfLink: Link = linkTo(mentoringControllerClass).withSelfRel()
            val mentoringId = "/${mentoring?.id}"
            val mentoringRel = "mentoring${mentoring?.id}"
            val mentoringLink: Link = linkTo(mentoringControllerClass).slash(mentoringId).withRel(mentoringRel)
            mentoring?.add(selfLink)
            mentoring?.add(mentoringLink)
        }

        val headers = HttpHeaders()
        headers.add("X-Page-Number", pagedModel.metadata?.number.toString())
        headers.add("X-Page-Size", pagedModel.metadata?.size.toString())
        return ResponseEntity.status(200).headers(headers).body(pagedModel)
    }

    override fun findMentoringById(id: Int): ResponseEntity<MentoringDTO> {
        val mentoring: MentoringDTO = mentoringService.findMentoringById(id)
        mentoring.add(linkTo(mentoringControllerClass).slash("/${mentoring.id}").withSelfRel())
        mentoring.add(linkTo(mentoringControllerClass).withRel("all"))
        return ResponseEntity.status(200).body(mentoring)
    }

    override fun updateMentoring(mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        val updatedMentoring: MentoringDTO = mentoringService.updateMentoring(mentoring)
        updatedMentoring.add(linkTo(mentoringControllerClass).slash("/${updatedMentoring.id}").withSelfRel())
        updatedMentoring.add(linkTo(mentoringControllerClass).withRel("all"))
        return ResponseEntity.status(200).body(updatedMentoring)
    }

    override fun deleteMentoring(id: Int): ResponseEntity<Unit> {
        mentoringService.deleteMentoring(id)
        return ResponseEntity.status(204).build()
    }
}