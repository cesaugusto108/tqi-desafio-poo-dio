package augusto108.ces.tqidesafiopoodio.dao;

import augusto108.ces.tqidesafiopoodio.dominio.entidades.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

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

    public Session getSession() {
        return session;
    }

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

    @Override
    public List<Bootcamp> listarBootcamps() {
        return session.createQuery("from Bootcamp order by id asc", Bootcamp.class).getResultList();
    }

    @Override
    public Bootcamp buscarBootcamp(Long id) {
        return session
                .createQuery("from Bootcamp b where id = :id", Bootcamp.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public Atividade buscarAtividade(Long id) {
        return session
                .createQuery("from Atividade a where id = :id", Atividade.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void persistirPessoas(List<Pessoa> pessoas) {
        for (Pessoa pessoa : pessoas) {
            session.persist(pessoa);
        }
    }
}
