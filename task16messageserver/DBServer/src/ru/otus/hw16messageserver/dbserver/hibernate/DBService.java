package ru.otus.hw16messageserver.dbserver.hibernate;

import ru.otus.hw16messageserver.messageserver.messagesystem.Member;

public interface DBService extends Member {
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
}
