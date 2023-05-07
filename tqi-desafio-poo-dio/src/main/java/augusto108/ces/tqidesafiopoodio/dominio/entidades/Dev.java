package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@DiscriminatorValue(value = "dev")
public final class Dev extends Pessoa {
    @Column(name = "nivel", length = 3)
    private Integer nivel;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "dev_bootcamp",
            joinColumns = @JoinColumn(name = "dev_id"), inverseJoinColumns = @JoinColumn(name = "bootcamp_id")
    )
    private final Set<Bootcamp> bootcamps = new LinkedHashSet<>();

    public Dev() {
    }

    public Dev(Nome nome, Integer idade, String email, Integer nivel) {
        super(nome, idade, email);
        this.nivel = nivel;
    }

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Set<Bootcamp> getBootcamps() {
        return bootcamps;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
