package ru.otus.hw15messagesystem.hibernate;

import ru.otus.hw15messagesystem.messagesystem.Sender;

public interface DBService extends Sender {
    <T extends DataSet> void save(T obj);
    <T extends DataSet> T load(long id, Class<T> clazz);
}