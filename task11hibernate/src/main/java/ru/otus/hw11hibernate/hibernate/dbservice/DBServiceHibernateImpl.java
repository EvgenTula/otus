package ru.otus.hw11hibernate.hibernate.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw11hibernate.DBService;
import ru.otus.hw11hibernate.DataSet;
import ru.otus.hw11hibernate.hibernate.dao.UsersDAO;
import ru.otus.hw11hibernate.hibernate.datasets.*;

import java.util.List;

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private final Configuration configuration;
    public DBServiceHibernateImpl(Configuration configuration) {
        this.configuration = configuration;
        sessionFactory = createSessionFactory(this.configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public void addClass(Class classInfo, String tableName) {

    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            return session.load(clazz, id);
        }
    }

    public <T extends DataSet> void save(T obj) {
        try (Session session = sessionFactory.openSession()) {
            session.save(obj);
        }
    }

    public void userSave (UserDataSetHibernate obj) {
        try (Session session = sessionFactory.openSession()) {
            UsersDAO dao = new UsersDAO(session);
            dao.save(obj);
        }
    }

    public UserDataSetHibernate userLoad(long id) {
        try (Session session = sessionFactory.openSession()) {
            UsersDAO dao = new UsersDAO(session);
            return dao.load(id);
        }
    }

    public UserDataSetHibernate userGetByName(String name) {
        try (Session session = sessionFactory.openSession()) {
            UsersDAO dao = new UsersDAO(session);
            return dao.readByName(name);
        }
    }

    public List<UserDataSetHibernate> userGetAllList() {
        try (Session session = sessionFactory.openSession()) {
            UsersDAO dao = new UsersDAO(session);
            return dao.readAll();
        }
    }
}
