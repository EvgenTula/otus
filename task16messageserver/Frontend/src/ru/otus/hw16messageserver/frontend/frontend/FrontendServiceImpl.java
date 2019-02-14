package ru.otus.hw16messageserver.frontend.frontend;

import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.service.MessageLoadData;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.service.MessageSaveData;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FrontendServiceImpl implements FrontendService {

    private final static String ALL_CLIENT = "ALL_CLIENT";
    private Address address;
    private MessageSystemContext messageSystemContext;
    private ConcurrentHashMap<UUID, DBServiceWebSocket> clientsMap;

    public FrontendServiceImpl(MessageSystemContext messageSystemContext,Address address) {
        this.messageSystemContext = messageSystemContext;
        this.address = address;
        this.clientsMap = new ConcurrentHashMap<>();
    }

    @Override
    public Address getAddress() {
        return this.address;
    }

    @Override
    public MessageSystemImpl getMessageSystem() {
        return messageSystemContext.getMessageSystem();
    }

    @Override
    public void sendDataClient(String uuid, String data) {
        if (uuid.equals(ALL_CLIENT)) {
            for (DBServiceWebSocket item : clientsMap.values()) {
                try {
                    item.getSession().getRemote().sendString(data);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            try {
                clientsMap.get(UUID.fromString(uuid)).getSession().getRemote().sendString(data);
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
        this.clientsMap.put(randomUUID,webSocket);
        return randomUUID.toString();
    }

    @Override
    public void removeClient(String uuid) {
        this.clientsMap.remove(uuid);
    }
}
