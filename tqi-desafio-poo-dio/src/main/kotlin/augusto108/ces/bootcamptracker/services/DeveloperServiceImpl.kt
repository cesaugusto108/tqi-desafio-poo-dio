package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.dao.DeveloperDao
import augusto108.ces.bootcamptracker.model.dto.DeveloperDTO
import augusto108.ces.bootcamptracker.model.entities.Developer
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
class DeveloperServiceImpl(
    private val developerDao: DeveloperDao,
    private val pagedResourcesAssembler: PagedResourcesAssembler<DeveloperDTO>
) : DeveloperService {
    override fun saveDeveloper(developer: Developer): DeveloperDTO =
        developerDao.saveDeveloper(developer).map(DeveloperDTO::class.java)

    override fun findAllDevelopers(page: Int, max: Int): PagedModel<EntityModel<DeveloperDTO>> {
        val developerDTOList: MutableList<DeveloperDTO> = ArrayList()

        developerDao.findAllDevelopers().forEach { developerDTOList.add(it.map(DeveloperDTO::class.java)) }

        return pagedResourcesAssembler.toModel(getPageRequest(page, max, developerDTOList))
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
        developerDao.updateDeveloper(developer).map(DeveloperDTO::class.java)

    override fun deleteDeveloper(id: Int): Unit = developerDao.deleteDeveloper(id)

    override fun activateDeveloper(id: Int) {
        developerDao.activateDeveloper(id)
    }

    override fun deactivateDeveloper(id: Int) {
        developerDao.deactivateDeveloper(id)
    }
}