package ru.otus.hw16messageserver.messageserver.messagesystem;

public interface DBServer extends Member {
    String saveData(String data);
    String loadUserList();
    void sendDataToFrontend(String uuid,String data);
    void sendDataToFrontend(String data);
}
