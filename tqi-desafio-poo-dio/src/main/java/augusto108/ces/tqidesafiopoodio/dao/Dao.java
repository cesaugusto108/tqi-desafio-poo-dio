package augusto108.ces.tqidesafiopoodio.dao;

import augusto108.ces.tqidesafiopoodio.dominio.entidades.Atividade;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Bootcamp;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Pessoa;
import org.hibernate.Session;

import java.util.List;

public interface Dao {
    Session getSession();

    void abrirSessao();

    void fecharSessao();

    List<Bootcamp> listarBootcamps();

    Bootcamp buscarBootcamp(Long id);

    Atividade buscarAtividade(Long id);

    void persistirPessoas(List<Pessoa> pessoas);
}
