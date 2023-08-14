package augusto108.ces.bootcamptracker.services

import augusto108.ces.bootcamptracker.model.dto.BootcampDTO
import augusto108.ces.bootcamptracker.model.entities.Bootcamp

interface BootcampService {
    fun saveBootcamp(bootcamp: Bootcamp): BootcampDTO

    fun findAllBootcamps(page: Int, max: Int): List<BootcampDTO>

    fun findBootcampById(id: Int): BootcampDTO

    fun bootcampById(id: Int): Bootcamp

    fun updateBootcamp(bootcamp: Bootcamp): BootcampDTO

    fun deleteBootcamp(id: Int): Any
}