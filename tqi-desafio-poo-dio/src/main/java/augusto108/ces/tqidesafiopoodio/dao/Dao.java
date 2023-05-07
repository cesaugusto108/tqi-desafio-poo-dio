package augusto108.ces.tqidesafiopoodio.dao;

import org.hibernate.Session;

public interface Dao {
    Session getSession();

    void abrirSessao();

    void fecharSessao();
}
