package augusto108.ces.tqidesafiopoodio.config;

import augusto108.ces.tqidesafiopoodio.dao.Dao;
import augusto108.ces.tqidesafiopoodio.dao.DaoImpl;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Bootcamp;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Curso;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Instrutor;
import augusto108.ces.tqidesafiopoodio.dominio.entidades.Mentoria;
import org.hibernate.Session;

import java.time.LocalDate;

public class CarregarDados {
    private static final Dao DAO = new DaoImpl();
    private static final Session session = DAO.getSession();

    public static void abrirSessao() {
        DAO.abrirSessao();
    }

    public static void encerrarSessao() {
        DAO.fecharSessao();
    }

    public static void persistirDados() {
        final LocalDate localDate = LocalDate.now();
        final String dataInicio = localDate.toString();
        final String dataEncerramento = localDate.plusDays(45).toString();

        final String tqiKotlin = "insert into bootcamp " +
                "(id, descricao, detalhamento, data_inicio, data_encerramento) " +
                "values (1, 'TQI Kotlin Backend', 'Java e Kotlin backend', '" + dataInicio + "', '" + dataEncerramento + "');";

        final String linuxExperience = "insert into bootcamp " +
                "(id, descricao, detalhamento) " +
                "values (2, 'Linux Experience', 'Aperfeiçoamento Linux');";

        final String curso1 = "insert into atividade (id, titulo, descricao, carga_horaria, atividade_tipo) " +
                "values (1, 'Sintaxe Java', 'Aprendendo a sintaxe Java', 300, 'curso');";

        final String curso2 = "insert into atividade (id, titulo, descricao, carga_horaria, atividade_tipo) " +
                "values (2, 'Bash', 'Bourne Again Shell', 90, 'curso');";

        final String mentoria1 = "insert into atividade (id, titulo, descricao, data, atividade_tipo) " +
                "values (3, 'Orientação a objetos', 'Orientação a objetos com Kotlin', '" + dataInicio + "', 'mentoria');";

        final String mentoria2 = "insert into atividade (id, titulo, descricao, data, atividade_tipo) " +
                "values (4, 'Sistemas operacionais Linux', 'Administração de sistemas operacionais Linux', '" + dataInicio + "', 'mentoria');";

        final String maria = "insert into pessoa " +
                "(idade, nivel, id, primeiro_nome, sobrenome, pessoa_tipo, email) " +
                "values (32, NULL, 1, 'Maria', 'Souza', 'instrutor', 'maria@email.com');";

        final String camila = "insert into pessoa " +
                "(idade, nivel, id, primeiro_nome, sobrenome, pessoa_tipo, email) " +
                "values (25, NULL, 2, 'Camila', 'Cavalcante', 'instrutor', 'camila@email.com');";

        session.createNativeQuery(tqiKotlin, Bootcamp.class).executeUpdate();
        session.createNativeQuery(linuxExperience, Bootcamp.class).executeUpdate();
        session.createNativeQuery(curso1, Curso.class).executeUpdate();
        session.createNativeQuery(curso2, Curso.class).executeUpdate();
        session.createNativeQuery(mentoria1, Mentoria.class).executeUpdate();
        session.createNativeQuery(mentoria2, Mentoria.class).executeUpdate();
        session.createNativeQuery(maria, Instrutor.class).executeUpdate();
        session.createNativeQuery(camila, Instrutor.class).executeUpdate();
    }
}
