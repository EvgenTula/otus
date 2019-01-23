package ru.otus.hw14war.webserver.hibernate;

public interface DBService {
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
