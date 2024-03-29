package augusto108.ces.bootcamptracker.model.helpers

import augusto108.ces.bootcamptracker.model.entities.BaseEntity
import augusto108.ces.bootcamptracker.model.entities.PersonBaseEntity
import org.springframework.beans.BeanUtils.copyProperties

object PropertyDuplicate {

    fun <T : BaseEntity> BaseEntity.copyTo(entity: T): T {
        copyProperties(this, entity)
        return entity
    }

    fun <T : PersonBaseEntity> PersonBaseEntity.copyTo(entity: T): T {
        copyProperties(this, entity)
        return entity
    }
}