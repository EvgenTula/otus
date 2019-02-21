package ru.otus.hw16messageserver.server.messageserver.messagesystem;

import java.util.List;

public interface DBServer extends Member {
    void start();
    int saveData(String data);
    String loadUserByid(int id);
    String loadUserList();
    void sendDataToFrontend(Address frontend,String uuid,String data);
}
