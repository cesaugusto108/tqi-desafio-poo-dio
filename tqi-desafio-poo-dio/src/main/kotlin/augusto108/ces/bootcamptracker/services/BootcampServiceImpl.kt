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
    override fun saveBootcamp(bootcamp: Bootcamp): BootcampDTO =
        DTOMapper.mapper().map(bootcampDao.saveBootcamp(bootcamp), BootcampDTO::class.java)

    override fun findAllBootcamps(page: Int, max: Int): List<BootcampDTO> {
        val bootcampDTOList: MutableList<BootcampDTO> = ArrayList()

        for (bootcamp in bootcampDao.findAllBootcamps(page, max)) {
            bootcampDTOList.add(
                DTOMapper.mapper().map(bootcamp, BootcampDTO::class.java)
            )
        }

        return bootcampDTOList
    }

    override fun findBootcampById(id: Int): BootcampDTO = try {
        DTOMapper.mapper().map(bootcampDao.findBootcampById(id), BootcampDTO::class.java)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultForQueryException("Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateBootcamp(bootcamp: Bootcamp): BootcampDTO =
        DTOMapper.mapper().map(bootcampDao.updateBootcamp(bootcamp), BootcampDTO::class.java)

    override fun deleteBootcamp(id: Int): Any = bootcampDao.deleteBootcamp(id)
}