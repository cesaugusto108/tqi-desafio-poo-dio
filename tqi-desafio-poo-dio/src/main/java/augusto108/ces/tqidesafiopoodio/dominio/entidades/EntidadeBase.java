package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.*;

import java.util.Objects;

@MappedSuperclass
public abstract sealed class EntidadeBase permits Atividade, Bootcamp, Pessoa {
    @Id
    @SequenceGenerator(name = "seq_gen", allocationSize = 1, initialValue = 3)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_gen")
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntidadeBase that = (EntidadeBase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
