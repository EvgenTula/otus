package ru.otus.hw16messageserver.server.messageserver.messagesystem.message.dbservice;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.DBServer;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;

public abstract class MessageToDBServer extends Message {

    public MessageToDBServer(Address from, Address to, String data) {
        super(from, to, data);
    }


    @Override
    public void exec(Member sender) {
        if (sender instanceof DBServer) {
            exec((DBServer)sender);
        }
    }

    public abstract void exec(DBServer dbService);
}
