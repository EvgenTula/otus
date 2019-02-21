package ru.otus.hw16messageserver.dbserver.dbservice.hibernate.dbservice;

import ru.otus.hw16messageserver.dbserver.dbservice.hibernate.DBService;
import ru.otus.hw16messageserver.dbserver.dbservice.hibernate.DataSet;
import ru.otus.hw16messageserver.dbserver.dbservice.hibernate.config.ConfigurationHibernate;
import ru.otus.hw16messageserver.dbserver.dbservice.hibernate.dao.UsersDAO;
import ru.otus.hw16messageserver.dbserver.dbservice.hibernate.datasets.UserDataSetHibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DBServiceHibernateImpl implements DBService {

    private final SessionFactory sessionFactory;
    private final Configuration configuration;
    //private Address address;
    //private MessageSystemContext messageSystemContext;
    public DBServiceHibernateImpl(ConfigurationHibernate cofig/*, MessageSystemContext messageSystemContext, Address address*/) {
        this.configuration = cofig.getConfiguration();
        this.sessionFactory = createSessionFactory(this.configuration);
        //this.messageSystemContext = messageSystemContext;
        //this.address = address;
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
/*
    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public MessageSystemImpl getMessageSystem() {
        return messageSystemContext.getMessageSystem();
    }*/
}
