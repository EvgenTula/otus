package ru.otus.hw14war.webserver.hibernate.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw14war.webserver.hibernate.DBService;
import ru.otus.hw14war.webserver.hibernate.DataSet;
import ru.otus.hw14war.webserver.hibernate.config.ConfigurationHibernate;
import ru.otus.hw14war.webserver.hibernate.dao.UsersDAO;
import ru.otus.hw14war.webserver.hibernate.datasets.UserDataSetHibernate;

import java.util.List;

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private final Configuration configuration;
    public DBServiceHibernateImpl(ConfigurationHibernate cofig) {
        this.configuration = cofig.getConfiguration();
        sessionFactory = createSessionFactory(this.configuration);
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        try (Session session = sessionFactory.openSession()) {
            return session.load(clazz, id);
        }
    }

    public <T extends DataSet> void save(T obj) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            transaction.commit();
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

    public void saveUser(UserDataSetHibernate user) {
        try (Session session = sessionFactory.openSession()) {
            UsersDAO dao = new UsersDAO(session);
            dao.save(user);
        }
    }
}
