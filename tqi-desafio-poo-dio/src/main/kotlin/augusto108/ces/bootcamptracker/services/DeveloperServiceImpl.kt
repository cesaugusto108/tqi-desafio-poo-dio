package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.DeveloperDao
import augusto108.ces.bootcamptracker.model.Developer
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeveloperServiceImpl(private val developerDao: DeveloperDao) : DeveloperService {
    override fun saveDeveloper(developer: Developer): Developer = developerDao.saveDeveloper(developer)

    override fun findAllDevelopers(): List<Developer> = developerDao.findAllDevelopers()

    override fun findDeveloperById(id: Int): Developer = developerDao.findDeveloperById(id)

    override fun updateDeveloper(developer: Developer): Developer = developerDao.updateDeveloper(developer)

    override fun deleteDeveloper(id: Int): Any = developerDao.deleteDeveloper(id)
}