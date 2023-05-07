package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
@DiscriminatorValue(value = "mentoria")
public final class Mentoria extends Atividade {
    @Column(name = "data")
    private LocalDate data;

    public Mentoria() {
    }

    public Mentoria(String titulo, String descricao, LocalDate data) {
        super(titulo, descricao);
        this.data = data;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Mentoria " + super.toString();
    }
}
