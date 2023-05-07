package augusto108.ces.tqidesafiopoodio.dominio.entidades;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "bootcamp")
public final class Bootcamp extends EntidadeBase {
    @Column(name = "descricao", nullable = false, unique = true, length = 45)
    private String descricao;

    @Column(name = "detalhamento", nullable = false)
    private String detalhamento;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_encerramento")
    private LocalDateTime dataEncerramento;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "bootcamp_atividades",
            joinColumns = @JoinColumn(name = "bootcamp_id"), inverseJoinColumns = @JoinColumn(name = "atividade_id")
    )
    private final Set<Atividade> atividades = new LinkedHashSet<>();

    public Bootcamp() {
    }

    public Bootcamp(String descricao, String detalhamento) {
        this.descricao = descricao;
        this.detalhamento = detalhamento;
        this.dataInicio = LocalDateTime.now();
        this.dataEncerramento = dataInicio.plusDays(45);
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDetalhamento() {
        return detalhamento;
    }

    public void setDetalhamento(String detalhamento) {
        this.detalhamento = detalhamento;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public LocalDateTime getDataEncerramento() {
        return dataEncerramento;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataEncerramento(LocalDateTime dataEncerramento) {
        this.dataEncerramento = dataEncerramento;
    }

    public Set<Atividade> getAtividades() {
        return atividades;
    }

    @Override
    public String toString() {
        return "Bootcamp " + descricao;
    }
}
