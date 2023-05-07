package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.*;

@Entity
@Table(name = "atividade")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "atividade_tipo", discriminatorType = DiscriminatorType.STRING)
public abstract non-sealed class Atividade extends EntidadeBase {
    @Column(name = "titulo", nullable = false, unique = true, length = 45)
    private String titulo;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    public Atividade() {
    }

    public Atividade(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return titulo;
    }
}
