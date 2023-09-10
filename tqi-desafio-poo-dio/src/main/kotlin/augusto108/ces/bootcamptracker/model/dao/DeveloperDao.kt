package augusto108.ces.bootcamptracker.model.dao

import augusto108.ces.bootcamptracker.model.entities.Developer
import java.util.*

interface DeveloperDao {
    fun saveDeveloper(developer: Developer): Developer

    fun findAllDevelopers(): List<Developer>

    fun findDeveloperById(id: UUID): Developer

    fun updateDeveloper(developer: Developer): Developer

    fun deleteDeveloper(id: UUID)

    fun activateDeveloper(id: UUID)

    fun deactivateDeveloper(id: UUID)
}