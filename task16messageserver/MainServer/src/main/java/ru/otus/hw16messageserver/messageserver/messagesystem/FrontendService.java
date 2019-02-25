package ru.otus.hw16messageserver.messageserver.messagesystem;

public interface FrontendService extends Member {
    void sendDataClient(String uuid, String data);
    void sendMessageLoadData(Address dbServer, String uuid);
}
