package augusto108.ces.tqidesafiopoodio.dao;

import augusto108.ces.tqidesafiopoodio.dominio.entidades.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DaoImpl implements Dao {
    private final SessionFactory sessionFactory = new Configuration()
            .configure("hibernate.cfg.xml")
            .addAnnotatedClass(Dev.class)
            .addAnnotatedClass(Instrutor.class)
            .addAnnotatedClass(Atividade.class)
            .addAnnotatedClass(Bootcamp.class)
            .addAnnotatedClass(Curso.class)
            .addAnnotatedClass(Mentoria.class)
            .buildSessionFactory();

    private final Session session = sessionFactory.getCurrentSession();

    @Override
    public void abrirSessao() {
        session.beginTransaction();
    }

    @Override
    public void fecharSessao() {
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }
}
