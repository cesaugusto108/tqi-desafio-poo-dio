package augusto108.ces.bootcamptracker.model

import jakarta.persistence.*

@MappedSuperclass
abstract class BaseEntity(
    @Id
    @SequenceGenerator(name = "seq_gen", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_gen")
    @Column(name = "id", nullable = false, unique = true) open var id: Int = 0
)