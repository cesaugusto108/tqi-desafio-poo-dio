package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.dao.DeveloperDao
import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
import augusto108.ces.bootcamptracker.model.mapper.personMap
import augusto108.ces.bootcamptracker.services.PageRequest.getPersonPageRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class DeveloperServiceImpl @Autowired constructor(
    private val developerDao: DeveloperDao,
    private val pagedResourcesAssembler: PagedResourcesAssembler<DeveloperDTO>
) : DeveloperService {

    override fun saveDeveloper(developer: Developer): DeveloperDTO {
        return developerDao.saveDeveloper(developer).personMap(DeveloperDTO::class.java)
    }

    override fun findAllDevelopers(page: Int, max: Int): PagedModel<EntityModel<DeveloperDTO>> {
        val developerDTOList: MutableList<DeveloperDTO> = ArrayList()
        developerDao.findAllDevelopers().forEach { developerDTOList.add(it.personMap(DeveloperDTO::class.java)) }
        return pagedResourcesAssembler.toModel(getPersonPageRequest(page, max, developerDTOList))
    }

    override fun findDeveloperById(id: String): DeveloperDTO = developerById(id).personMap(DeveloperDTO::class.java)

    override fun developerById(id: String): Developer =
        try {
            developerDao.findDeveloperById(UUID.fromString(id))
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        }

    override fun updateDeveloper(developer: Developer): DeveloperDTO {
        return developerDao.updateDeveloper(developer).personMap(DeveloperDTO::class.java)
    }

    override fun deleteDeveloper(id: String): Unit = developerDao.deleteDeveloper(UUID.fromString(id))

    override fun activateDeveloper(id: String) {
        developerDao.activateDeveloper(UUID.fromString(id))
    }

    override fun deactivateDeveloper(id: String) {
        developerDao.deactivateDeveloper(UUID.fromString(id))
    }
}