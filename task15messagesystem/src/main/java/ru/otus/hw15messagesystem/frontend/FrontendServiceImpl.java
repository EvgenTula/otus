package ru.otus.hw15messagesystem.frontend;

import ru.otus.hw15messagesystem.messagesystem.Address;
import ru.otus.hw15messagesystem.messagesystem.MessageSystem;
import ru.otus.hw15messagesystem.messagesystem.MessageSystemContext;
import ru.otus.hw15messagesystem.messagesystem.message.service.MessageLoadData;
import ru.otus.hw15messagesystem.messagesystem.message.service.MessageSaveData;
import ru.otus.hw15messagesystem.websocket.DBServiceWebSocket;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class FrontendServiceImpl implements FrontendService {

    private Address address;
    private MessageSystemContext messageSystemContext;
    private ConcurrentHashMap<UUID, DBServiceWebSocket> listClient;

    public FrontendServiceImpl(MessageSystemContext messageSystemContext,Address address) {
        this.messageSystemContext = messageSystemContext;
        this.address = address;
        this.listClient = new ConcurrentHashMap<>();
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public MessageSystem getMessageSystem() {
        return messageSystemContext.getMessageSystem();
    }

    public void sendDataAllClient(String data) {
        for (DBServiceWebSocket item : listClient.values()) {
            try {
                item.getSession().getRemote().sendString(data);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void sendSaveUserMessage(String data) {
        messageSystemContext.getMessageSystem().sendMessage(
                new MessageSaveData(this.getAddress(),messageSystemContext.getDbServiceAddress(),data)
        );
    }

    @Override
    public void sendGetUsersListMessage(String uuid) {
        this.messageSystemContext.getMessageSystem().sendMessage(
                new MessageLoadData(this.getAddress(),
                                    messageSystemContext.getDbServiceAddress(),uuid));
    }

    @Override
    public String addClient(DBServiceWebSocket webSocket) {
        UUID randomUUID = UUID.randomUUID();
        this.listClient.put(randomUUID,webSocket);
        return randomUUID.toString();
    }

    @Override
    public void removeClient(String uuid) {
        this.listClient.remove(uuid);
    }
}
