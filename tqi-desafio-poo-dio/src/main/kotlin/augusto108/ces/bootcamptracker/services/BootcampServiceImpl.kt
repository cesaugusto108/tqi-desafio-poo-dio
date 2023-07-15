package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.BootcampDao
import augusto108.ces.bootcamptracker.dto.BootcampDTO
import augusto108.ces.bootcamptracker.dto.mapper.DTOMapper
import augusto108.ces.bootcamptracker.entities.Bootcamp
import augusto108.ces.bootcamptracker.exceptions.NoResultForQueryException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BootcampServiceImpl(private val bootcampDao: BootcampDao) : BootcampService {
    override fun saveBootcamp(bootcamp: Bootcamp): BootcampDTO = bootcampDao.saveBootcamp(bootcamp).map()

    override fun findAllBootcamps(page: Int, max: Int): List<BootcampDTO> {
        val bootcampDTOList: MutableList<BootcampDTO> = ArrayList()

        bootcampDao.findAllBootcamps(page, max).forEach { bootcampDTOList.add(it.map()) }

        return bootcampDTOList
    }

    override fun findBootcampById(id: Int): BootcampDTO = bootcampById(id).map()

    override fun bootcampById(id: Int): Bootcamp =
        try {
            bootcampDao.findBootcampById(id)
        } catch (e: EmptyResultDataAccessException) {
            throw NoResultForQueryException("Id: $id")
        } catch (e: NumberFormatException) {
            throw NumberFormatException()
        }

    override fun updateBootcamp(bootcamp: Bootcamp): BootcampDTO = bootcampDao.updateBootcamp(bootcamp).map()

    override fun deleteBootcamp(id: Int): Any = bootcampDao.deleteBootcamp(id)

    private fun Bootcamp.map(): BootcampDTO = DTOMapper.mapper().map(this, BootcampDTO::class.java)
}