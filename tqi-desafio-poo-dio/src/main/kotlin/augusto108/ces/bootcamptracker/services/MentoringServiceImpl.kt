package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.MentoringDao
import augusto108.ces.bootcamptracker.entities.Mentoring
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MentoringServiceImpl(private val mentoringDao: MentoringDao) : MentoringService {
    override fun saveMentoring(mentoring: Mentoring): Mentoring = mentoringDao.saveMentoring(mentoring)

    override fun findAllMentoring(page: Int, max: Int): List<Mentoring> = mentoringDao.findAllMentoring(page, max)

    override fun findMentoringById(id: Int): Mentoring = try {
        mentoringDao.findMentoringById(id)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultForQueryException("Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateMentoring(mentoring: Mentoring): Mentoring = mentoringDao.updateMentoring(mentoring)

    override fun deleteMentoring(id: Int): Any = mentoringDao.deleteMentoring(id)
}