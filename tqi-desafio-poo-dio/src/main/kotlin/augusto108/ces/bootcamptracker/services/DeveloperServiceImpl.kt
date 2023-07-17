package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.DeveloperDao
import augusto108.ces.bootcamptracker.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.dto.mapper.map
import augusto108.ces.bootcamptracker.entities.Developer
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DeveloperServiceImpl(private val developerDao: DeveloperDao) : DeveloperService {
    override fun saveDeveloper(developer: Developer): DeveloperDTO =
        developerDao.saveDeveloper(developer).map(DeveloperDTO::class.java)

    override fun findAllDevelopers(page: Int, max: Int): List<DeveloperDTO> {
        val developerDTOList: MutableList<DeveloperDTO> = ArrayList()

        developerDao.findAllDevelopers(page, max).forEach { developerDTOList.add(it.map(DeveloperDTO::class.java)) }

        return developerDTOList
    }

    override fun findDeveloperById(id: Int): DeveloperDTO = developerById(id).map(DeveloperDTO::class.java)

    override fun developerById(id: Int): Developer =
        try {
            developerDao.findDeveloperById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        } catch (e: NumberFormatException) {
            throw NumberFormatException()
        }

    override fun updateDeveloper(developer: Developer): DeveloperDTO =
        developerDao.updateDeveloper(developer.copyProperties(developer)).map(DeveloperDTO::class.java)

    override fun deleteDeveloper(id: Int): Any = developerDao.deleteDeveloper(id)
}