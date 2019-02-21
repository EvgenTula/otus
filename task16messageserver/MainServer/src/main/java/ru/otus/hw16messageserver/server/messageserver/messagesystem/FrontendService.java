package ru.otus.hw16messageserver.server.messageserver.messagesystem;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;

public interface FrontendService extends Member {
    void start();
    void addClient(String uuid);
    void sendDataClient(String uuid, String data);
    void sendMessageLoadData(Address dbServer, String uuid);
}
