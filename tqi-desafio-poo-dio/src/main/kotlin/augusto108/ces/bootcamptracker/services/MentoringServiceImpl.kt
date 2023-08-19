package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.dao.MentoringDao
import augusto108.ces.bootcamptracker.model.dto.MentoringDTO
import augusto108.ces.bootcamptracker.model.entities.Mentoring
import augusto108.ces.bootcamptracker.model.mapper.map
import augusto108.ces.bootcamptracker.services.PageRequest.getPageRequest
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MentoringServiceImpl(
    private val mentoringDao: MentoringDao,
    private val pagedResourcesAssembler: PagedResourcesAssembler<MentoringDTO>
) : MentoringService {
    override fun saveMentoring(mentoring: Mentoring): MentoringDTO =
        mentoringDao.saveMentoring(mentoring).map(MentoringDTO::class.java)

    override fun findAllMentoring(page: Int, max: Int): PagedModel<EntityModel<MentoringDTO>> {
        val mentoringDTOList: MutableList<MentoringDTO> = ArrayList()

        mentoringDao.findAllMentoring().forEach { mentoringDTOList.add(it.map(MentoringDTO::class.java)) }

        return pagedResourcesAssembler.toModel(getPageRequest(page, max, mentoringDTOList))
    }

    override fun findMentoringById(id: Int): MentoringDTO = mentoringById(id).map(MentoringDTO::class.java)

    override fun mentoringById(id: Int): Mentoring =
        try {
            mentoringDao.findMentoringById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        } catch (e: NumberFormatException) {
            throw NumberFormatException()
        }

    override fun updateMentoring(mentoring: Mentoring): MentoringDTO =
        mentoringDao.updateMentoring(mentoring).map(MentoringDTO::class.java)

    override fun deleteMentoring(id: Int): Unit = mentoringDao.deleteMentoring(id)
}