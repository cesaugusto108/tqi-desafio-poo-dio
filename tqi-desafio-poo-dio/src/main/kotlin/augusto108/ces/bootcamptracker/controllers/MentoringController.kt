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
        mentoringService.saveMentoring(mentoring).also {
            it.add(linkTo(mentoringControllerClass).slash("/${mentoring.id}").withSelfRel())
            it.add(linkTo(mentoringControllerClass).withRel("all"))
            val uri: URI = it.getRequiredLink(IanaLinkRelations.SELF).toUri()
            return ResponseEntity.status(201).location(uri).body(it)
        }
    }

    override fun findAllMentoring(page: Int, max: Int): ResponseEntity<PagedModel<EntityModel<MentoringDTO>>> {
        mentoringService.findAllMentoring(page, max).also {
            val mentoringDTOList: MutableList<MentoringDTO?> = mutableListOf()
            for (entityModel: EntityModel<MentoringDTO> in it) mentoringDTOList.add(entityModel.content)
            for (mentoring: MentoringDTO? in mentoringDTOList) {
                val selfLink: Link = linkTo(mentoringControllerClass).withSelfRel()
                val mentoringId = "/${mentoring?.id}"
                val mentoringRel = "mentoring${mentoring?.id}"
                val mentoringLink: Link = linkTo(mentoringControllerClass).slash(mentoringId).withRel(mentoringRel)
                mentoring?.add(selfLink)
                mentoring?.add(mentoringLink)
            }
            val headers = HttpHeaders()
            headers.add("X-Page-Number", it.metadata?.number.toString())
            headers.add("X-Page-Size", it.metadata?.size.toString())
            return ResponseEntity.status(200).headers(headers).body(it)
        }
    }

    override fun findMentoringById(id: Int): ResponseEntity<MentoringDTO> {
        mentoringService.findMentoringById(id).also {
            it.add(linkTo(mentoringControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(mentoringControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
    }

    override fun updateMentoring(mentoring: Mentoring): ResponseEntity<MentoringDTO> {
        mentoringService.updateMentoring(mentoring).also {
            it.add(linkTo(mentoringControllerClass).slash("/${it.id}").withSelfRel())
            it.add(linkTo(mentoringControllerClass).withRel("all"))
            return ResponseEntity.status(200).body(it)
        }
    }

    override fun deleteMentoring(id: Int): ResponseEntity<Unit> {
        mentoringService.deleteMentoring(id)
        return ResponseEntity.status(204).build()
    }
}