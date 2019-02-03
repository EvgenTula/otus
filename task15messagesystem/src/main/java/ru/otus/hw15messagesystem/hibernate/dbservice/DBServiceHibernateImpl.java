package ru.otus.hw15messagesystem.hibernate.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.hibernate.DataSet;
import ru.otus.hw15messagesystem.hibernate.config.ConfigurationHibernate;
import ru.otus.hw15messagesystem.hibernate.dao.UsersDAO;
import ru.otus.hw15messagesystem.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Sender;

import java.util.List;

public class DBServiceHibernateImpl implements DBService, Sender {

    private final SessionFactory sessionFactory;
    private final Configuration configuration;
    private final Address address;
    public DBServiceHibernateImpl(ConfigurationHibernate cofig, Address address) {
        this.configuration = cofig.getConfiguration();
        this.sessionFactory = createSessionFactory(this.configuration);
        this.address = address;
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

    @Override
    public Address getAddress() {
        return this.address;
    }
}
