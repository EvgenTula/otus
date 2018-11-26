package ru.otus.hw10orm;

import ru.otus.hw10orm.dataset.DataSet;

import java.util.HashMap;
import java.util.List;

public interface DBService {
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
    <T extends DataSet> HashMap<String, Object> getFieldsValue(T obj);
}
