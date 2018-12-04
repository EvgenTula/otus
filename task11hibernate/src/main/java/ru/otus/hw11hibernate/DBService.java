package ru.otus.hw11hibernate;

public interface DBService {
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
