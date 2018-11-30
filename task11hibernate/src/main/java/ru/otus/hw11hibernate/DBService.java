package ru.otus.hw11hibernate;

import ru.otus.hw11hibernate.datasets.DataSet;

public interface DBService {
    void addClass(Class classInfo, String tableName);
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
