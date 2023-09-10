package augusto108.ces.bootcamptracker.model.entities

import jakarta.persistence.*
import org.springframework.beans.BeanUtils
import java.util.*

@MappedSuperclass
abstract class PersonBaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    open var id: UUID? = null
) {
    fun <T : PersonBaseEntity> copyProperties(entity: T): T {
        BeanUtils.copyProperties(this, entity)

        return entity
    }
}