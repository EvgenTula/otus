package ru.otus.hw15messagesystem.messagesystem.message;

import ru.otus.hw15messagesystem.hibernate.DBService;
import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.Message;

public abstract class MessageToDBService extends Message {

    public MessageToDBService(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(Address address) {
        if (address instanceof DBService) {
            exec((DBService)address);
        }
    }

    public abstract void exec(DBService dbService);
}
