package ru.otus.wh11hibernate;

import ru.otus.hw10orm.dataset.DataSet;

public interface DBService {
    void addClass(Class classInfo, String tableName);
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
