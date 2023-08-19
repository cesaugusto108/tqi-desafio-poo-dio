package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import augusto108.ces.bootcamptracker.model.dao.BootcampDao
import augusto108.ces.bootcamptracker.model.dto.BootcampDTO
import augusto108.ces.bootcamptracker.model.entities.Bootcamp
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
class BootcampServiceImpl(
    private val bootcampDao: BootcampDao,
    private val pagedResourcesAssembler: PagedResourcesAssembler<BootcampDTO>
) : BootcampService {
    override fun saveBootcamp(bootcamp: Bootcamp): BootcampDTO =
        bootcampDao.saveBootcamp(bootcamp).map(BootcampDTO::class.java)

    override fun findAllBootcamps(page: Int, max: Int): PagedModel<EntityModel<BootcampDTO>> {
        val bootcampDTOList: MutableList<BootcampDTO> = ArrayList()

        bootcampDao.findAllBootcamps().forEach { bootcampDTOList.add(it.map(BootcampDTO::class.java)) }

        return pagedResourcesAssembler.toModel(getPageRequest(page, max, bootcampDTOList))
    }

    override fun findBootcampById(id: Int): BootcampDTO = bootcampById(id).map(BootcampDTO::class.java)

    override fun bootcampById(id: Int): Bootcamp =
        try {
            bootcampDao.findBootcampById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        } catch (e: NumberFormatException) {
            throw NumberFormatException()
        }

    override fun updateBootcamp(bootcamp: Bootcamp): BootcampDTO =
        bootcampDao.updateBootcamp(bootcamp).map(BootcampDTO::class.java)

    override fun deleteBootcamp(id: Int): Unit = bootcampDao.deleteBootcamp(id)
}