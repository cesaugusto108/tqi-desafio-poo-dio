package augusto108.ces.tqidesafiopoodio.config;

import augusto108.ces.tqidesafiopoodio.dao.Dao;
import augusto108.ces.tqidesafiopoodio.dao.DaoImpl;

public class CarregarDados {
    private static final Dao DAO = new DaoImpl();

    public static void abrirSessao() {
        DAO.abrirSessao();
    }

    public static void encerrarSessao() {
        DAO.fecharSessao();
    }
}
