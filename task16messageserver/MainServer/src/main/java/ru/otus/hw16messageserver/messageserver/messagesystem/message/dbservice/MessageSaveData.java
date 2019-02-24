package ru.otus.hw16messageserver.messageserver.messagesystem.message.dbservice;

import ru.otus.hw16messageserver.messageserver.messagesystem.DBServer;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;

public class MessageSaveData extends MessageToDBServer {

    public MessageSaveData(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(DBServer dbServer) {
        String gsonObject = dbServer.saveData(this.getData());
        dbServer.sendDataToFrontend(gsonObject);
    }
}
