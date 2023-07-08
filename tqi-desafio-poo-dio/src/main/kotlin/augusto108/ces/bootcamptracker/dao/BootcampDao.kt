package augusto108.ces.bootcamptracker.dao

import augusto108.ces.bootcamptracker.entities.Bootcamp

interface BootcampDao {
    fun saveBootcamp(bootcamp: Bootcamp): Bootcamp

    fun findAllBootcamps(page: Int, max: Int): List<Bootcamp>

    fun findBootcampById(id: Int): Bootcamp

    fun updateBootcamp(bootcamp: Bootcamp): Bootcamp

    fun deleteBootcamp(id: Int): Any
}