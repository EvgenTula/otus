package ru.otus.hw14war.hibernate.dbservice;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import ru.otus.hw14war.hibernate.DBService;
import ru.otus.hw14war.hibernate.DataSet;
import ru.otus.hw14war.hibernate.config.ConfigurationHibernate;
import ru.otus.hw14war.hibernate.dao.UsersDAO;
import ru.otus.hw14war.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw14war.mycacheengine.CacheEngine;
import ru.otus.hw14war.mycacheengine.Element;

import java.util.List;

public class DBServiceHibernateImpl<T extends DataSet> implements DBService {

    private SessionFactory sessionFactory;
    private Configuration configuration;
    private CacheEngine<Long, Element<Long, T>> cacheEngine;
    public DBServiceHibernateImpl(ConfigurationHibernate config, CacheEngine cacheEngine) {
        this.configuration = config.getConfiguration();
        this.sessionFactory = createSessionFactory(this.configuration);
        this.cacheEngine = cacheEngine;
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        if (this.cacheEngine.get(id) == null) {
            try (Session session = sessionFactory.openSession()) {
                T obj = session.load(clazz, id);
                if (obj == null) {
                    return null;
                }
                this.cacheEngine.put(id,new Element(id,obj));
            }
        }
        return (T) this.cacheEngine.get(id).getVal();
    }

    public <T extends DataSet> void save(T obj) {
        this.cacheEngine.remove(obj.getId());
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.saveOrUpdate(obj);
            transaction.commit();
        }
        this.cacheEngine.put(obj.getId(),new Element(obj.getId(),obj));
    }

    public UserDataSetHibernate loadUser(long id) {
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

    public void saveUser(UserDataSetHibernate user) {
        try (Session session = sessionFactory.openSession()) {
            UsersDAO dao = new UsersDAO(session);
            dao.save(user);
        }
    }
}
