package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoa")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "pessoa_tipo", discriminatorType = DiscriminatorType.STRING)
public abstract non-sealed class Pessoa extends EntidadeBase {
    @Embedded
    private Nome nome;

    @Column(name = "idade", length = 3)
    private Integer idade;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    public Pessoa() {
    }

    public Pessoa(Nome nome, Integer idade, String email) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
    }

    public Nome getNome() {
        return nome;
    }

    public void setNome(Nome nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return super.getId() + " - " + nome.toString();
    }
}
