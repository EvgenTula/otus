package ru.otus.hw16messageserver.messageserver.messagesystem.message.dbservice;


import ru.otus.hw16messageserver.messageserver.messagesystem.DBServer;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;

public class MessageLoadData extends MessageToDBServer {
    private String uuid;
    public MessageLoadData(Address from, Address to, String uuid) {
        super(from, to, uuid);
        this.uuid = uuid;
}

    @Override
    public void exec(DBServer dbServer) {
        String usersData = dbServer.loadUserList();
        dbServer.sendDataToFrontend(uuid,usersData);
    }
}
