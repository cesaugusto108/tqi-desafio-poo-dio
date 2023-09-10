package augusto108.ces.bootcamptracker.model.entities

import jakarta.persistence.*
import java.util.*

@MappedSuperclass
abstract class PersonBaseEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, unique = true)
    open var id: UUID? = null
)