package ru.otus.wh11hibernate;


import ru.otus.hw10orm.dataset.DataSet;

public class DBServiceImplHibernate implements DBService {

    public void addClass(Class classInfo, String tableName) {

    }

    public <T extends DataSet> void save(T obj) {

    }

    public <T extends DataSet> T load(long id, Class<T> clazz) {
        return null;
    }
}
