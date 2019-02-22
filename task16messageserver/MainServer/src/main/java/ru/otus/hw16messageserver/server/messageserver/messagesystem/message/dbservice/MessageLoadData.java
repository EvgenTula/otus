package ru.otus.hw16messageserver.server.messageserver.messagesystem.message.dbservice;


import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.DBServer;

public class MessageLoadData extends MessageToDBServer {
    private String uuid;
    public MessageLoadData(Address from, Address to, String uuid) {
        super(from, to, uuid);
        this.uuid = uuid;
    }

    @Override
    public void exec(DBServer dbServer) {
        String usersData = dbServer.loadUserList();
        dbServer.sendDataToFrontend(getFrom(),uuid,usersData);
    }
}
