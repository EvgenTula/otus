package ru.otus.hw16messageserver.messageserver.messagesystem;

public interface FrontendService extends Member {
    void start();
    void sendDataClient(String uuid, String data);
    void sendMessageLoadData(Address dbServer, String uuid);
    void sendSaveData(String uuid);
    //void setDBServerAddress(String jsonAdrress);
}
