package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class Nome {
    @Column(name = "primeiro_nome", nullable = false, length = 30)
    private String primeiroNome;

    @Column(name = "sobrenome", nullable = false, length = 30)
    private String sobrenome;

    public Nome() {
    }

    public Nome(String primeiroNome, String sobrenome) {
        this.primeiroNome = primeiroNome;
        this.sobrenome = sobrenome;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String primeiroNome) {
        this.primeiroNome = primeiroNome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    @Override
    public String toString() {
        return primeiroNome + " " + sobrenome;
    }
}
