package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "curso")
public final class Curso extends Atividade {
    @Column(name = "carga_horaria")
    private Integer cargaHoraria;

    public Curso() {
    }

    public Curso(String titulo, String descricao, Integer cargaHoraria) {
        super(titulo, descricao);
        this.cargaHoraria = cargaHoraria;
    }

    public Integer getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(Integer cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    @Override
    public String toString() {
        return "Curso " + super.toString();
    }
}
