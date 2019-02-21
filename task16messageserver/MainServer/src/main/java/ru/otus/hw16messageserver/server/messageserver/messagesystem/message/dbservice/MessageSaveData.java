package ru.otus.hw16messageserver.server.messageserver.messagesystem.message.dbservice;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.DBServer;

public class MessageSaveData extends MessageToDBServer {

    private final static String ALL_CLIENT = "ALL_CLIENT";
    
    public MessageSaveData(Address from, Address to, String data) {
        super(from, to, data);
    }

    @Override
    public void exec(DBServer dbServer) {
        int recordId = dbServer.saveData(getData());
        String userData = dbServer.loadUserByid(recordId);
        dbServer.sendDataToFrontend("ALL_CLIENT", userData);
    }
}
