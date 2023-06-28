package augusto108.ces.bootcamptracker.model

import jakarta.persistence.*

@MappedSuperclass
abstract class BaseEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false, unique = true) open var id: Int = 0
)