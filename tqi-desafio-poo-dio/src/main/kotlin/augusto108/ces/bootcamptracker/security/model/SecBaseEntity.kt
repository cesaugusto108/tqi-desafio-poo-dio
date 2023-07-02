package augusto108.ces.bootcamptracker.security.model

import jakarta.persistence.*

@MappedSuperclass
abstract class SecBaseEntity(
    @Id
    @SequenceGenerator(name = "sec_seq_gen", allocationSize = 1, initialValue = 1000)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "sec_seq_gen")
    @Column(name = "id", nullable = false, unique = true) open var id: Int = 0
)