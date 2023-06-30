package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.MentoringDao
import augusto108.ces.bootcamptracker.model.Mentoring
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MentoringServiceImpl(private val mentoringDao: MentoringDao) : MentoringService {
    override fun saveMentoring(mentoring: Mentoring): Mentoring = mentoringDao.saveMentoring(mentoring)

    override fun findAllMentoring(page: Int, max: Int): List<Mentoring> = mentoringDao.findAllMentoring(page, max)

    override fun findMentoringById(id: Int): Mentoring = mentoringDao.findMentoringById(id)

    override fun updateMentoring(mentoring: Mentoring): Mentoring = mentoringDao.updateMentoring(mentoring)

    override fun deleteMentoring(id: Int): Any = mentoringDao.deleteMentoring(id)
}