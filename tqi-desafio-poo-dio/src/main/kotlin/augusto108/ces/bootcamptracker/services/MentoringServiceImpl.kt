package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.MentoringDao
import augusto108.ces.bootcamptracker.dto.MentoringDTO
import augusto108.ces.bootcamptracker.dto.mapper.DTOMapper
import augusto108.ces.bootcamptracker.entities.Mentoring
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MentoringServiceImpl(private val mentoringDao: MentoringDao) : MentoringService {
    override fun saveMentoring(mentoring: Mentoring): MentoringDTO =
        DTOMapper.mapper().map(mentoringDao.saveMentoring(mentoring), MentoringDTO::class.java)

    override fun findAllMentoring(page: Int, max: Int): List<MentoringDTO> {
        val mentoringDTOList: MutableList<MentoringDTO> = ArrayList()

        for (mentoring in mentoringDao.findAllMentoring(page, max)) {
            mentoringDTOList.add(DTOMapper.mapper().map(mentoring, MentoringDTO::class.java))
        }

        return mentoringDTOList
    }

    override fun findMentoringById(id: Int): MentoringDTO =
        DTOMapper.mapper().map(mentoringById(id), MentoringDTO::class.java)

    override fun mentoringById(id: Int): Mentoring = try {
        mentoringDao.findMentoringById(id)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultForQueryException("Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateMentoring(mentoring: Mentoring): MentoringDTO =
        DTOMapper.mapper().map(mentoringDao.updateMentoring(mentoring), MentoringDTO::class.java)

    override fun deleteMentoring(id: Int): Any = mentoringDao.deleteMentoring(id)
}