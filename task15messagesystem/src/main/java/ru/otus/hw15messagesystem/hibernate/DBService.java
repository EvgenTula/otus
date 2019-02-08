package ru.otus.hw15messagesystem.hibernate;

import ru.otus.hw15messagesystem.messagesystem.Member;

public interface DBService extends Member {
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
