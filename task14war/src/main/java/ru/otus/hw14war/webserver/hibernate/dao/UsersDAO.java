package ru.otus.hw14war.webserver.hibernate.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import ru.otus.hw14war.webserver.hibernate.datasets.UserDataSetHibernate;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UsersDAO {
    private Session session;

    public UsersDAO(Session session) {
        this.session = session;
    }

    public void save(UserDataSetHibernate dataSet) {
        session.saveOrUpdate(dataSet);
    }


    public UserDataSetHibernate load(long id) {
        return session.load(UserDataSetHibernate.class, id);
    }

    public UserDataSetHibernate readByName(String name) {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSetHibernate> criteria = builder.createQuery(UserDataSetHibernate.class);
        Root<UserDataSetHibernate> from = criteria.from(UserDataSetHibernate.class);
        criteria.where(builder.equal(from.get("name"), name));
        Query<UserDataSetHibernate> query = session.createQuery(criteria);
        return query.uniqueResult();
    }

    public List<UserDataSetHibernate> readAll() {
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<UserDataSetHibernate> criteria = builder.createQuery(UserDataSetHibernate.class);
        criteria.from(UserDataSetHibernate.class);
        return session.createQuery(criteria).list();
    }
}
