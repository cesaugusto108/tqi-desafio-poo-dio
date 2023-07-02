package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.DeveloperDao
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.Developer
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeveloperServiceImpl(private val developerDao: DeveloperDao) : DeveloperService {
    override fun saveDeveloper(developer: Developer): Developer = developerDao.saveDeveloper(developer)

    override fun findAllDevelopers(page: Int, max: Int): List<Developer> = developerDao.findAllDevelopers(page, max)

    override fun findDeveloperById(id: Int): Developer = try {
        developerDao.findDeveloperById(id)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultForQueryException("Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateDeveloper(developer: Developer): Developer = developerDao.updateDeveloper(developer)

    override fun deleteDeveloper(id: Int): Any = developerDao.deleteDeveloper(id)
}