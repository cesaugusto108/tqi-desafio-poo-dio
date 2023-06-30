package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.dao.BootcampDao
import augusto108.ces.bootcamptracker.model.Bootcamp
import jakarta.persistence.NoResultException
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BootcampServiceImpl(private val bootcampDao: BootcampDao) : BootcampService {
    override fun saveBootcamp(bootcamp: Bootcamp): Bootcamp = bootcampDao.saveBootcamp(bootcamp)

    override fun findAllBootcamps(page: Int, max: Int): List<Bootcamp> = bootcampDao.findAllBootcamps(page, max)

    override fun findBootcampById(id: Int): Bootcamp = try {
        bootcampDao.findBootcampById(id)
    } catch (e: EmptyResultDataAccessException) {
        throw NoResultException("No result for query. Id: $id")
    } catch (e: NumberFormatException) {
        throw NumberFormatException()
    }

    override fun updateBootcamp(bootcamp: Bootcamp): Bootcamp = bootcampDao.updateBootcamp(bootcamp)

    override fun deleteBootcamp(id: Int): Any = bootcampDao.deleteBootcamp(id)
}