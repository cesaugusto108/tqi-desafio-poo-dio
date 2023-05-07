package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "instrutor")
public final class Instrutor extends Pessoa {
    public Instrutor() {
    }

    public Instrutor(Nome nome, Integer idade, String email) {
        super(nome, idade, email);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
