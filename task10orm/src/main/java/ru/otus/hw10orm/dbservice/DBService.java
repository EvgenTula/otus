package ru.otus.hw10orm.dbservice;

import ru.otus.hw10orm.dataset.DataSet;

public interface DBService {
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
