package ru.otus.hw16messageserver.messageserver.messagesystem.message.dbservice;

import ru.otus.hw16messageserver.messageserver.messagesystem.DBServer;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;

public class MessageSaveData extends MessageToDBServer {

    private final static String ALL_CLIENT = "ALL_CLIENT";
    private String uuid;
    public MessageSaveData(Address from, Address to, String data) {
        super(from, to, data);
        this.uuid = ALL_CLIENT;
    }

    public MessageSaveData(Address from, Address to, String data, String uuid) {
        super(from, to, data);
        this.uuid = uuid;
    }

    @Override
    public void exec(DBServer dbServer) {
        int recordId = dbServer.saveData(getData());
        String userData = dbServer.loadUserByid(recordId);
        dbServer.sendDataToFrontend(uuid, userData);
    }
}
