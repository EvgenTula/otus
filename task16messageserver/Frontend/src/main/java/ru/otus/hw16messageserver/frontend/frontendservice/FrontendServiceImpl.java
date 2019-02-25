package ru.otus.hw16messageserver.frontend.frontendservice;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.hw16messageserver.frontend.websocket.ServiceWebSocket;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.*;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.dbservice.MessageLoadData;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.dbservice.MessageSaveData;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FrontendServiceImpl implements FrontendService {

    private final static String ALL_CLIENT = "ALL_CLIENT";
    private static final int THREADS_NUMBER = 1;

    private ConcurrentHashMap<UUID, ServiceWebSocket> clientsMap;
    private final ExecutorService executor;
    private MessageSystemContext messageSystemContext;

    public FrontendServiceImpl(MessageSystemContext messageSystemContext) {
        this.messageSystemContext = messageSystemContext;
        this.clientsMap = new ConcurrentHashMap<>();
        this.executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    public void start() {
        messageSystemContext.getWorker().init();
        MessageToRegisterSocketClient messageToRegister = new MessageToRegisterSocketClient(this.getAddress(), null, "");
        messageSystemContext.getWorker().send(messageToRegister.getJsonObject());
        executor.submit(this::processing);
    }

    public void processing() {
        while (true) {
            String messageBody = null;
            try {
                messageBody =  messageSystemContext.getWorker().take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            while (messageBody != null) {
                try {
                    JSONParser jsonParser = new JSONParser();

                    JSONObject jsonObject = (JSONObject) jsonParser.parse(messageBody);
                    String className = (String) jsonObject.get("className");
                    String gsonData = (String) jsonObject.get("data");
                    Class<?> msgClass = Class.forName(className);
                    var messageObj = new Gson().fromJson(gsonData, msgClass);
                    if (messageObj instanceof Message) {
                        ((Message) messageObj).exec(this);
                    }
                    messageBody =  messageSystemContext.getWorker().take();
               } catch (ParseException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public String addClient(ServiceWebSocket webSocket) {
        UUID randomUUID = UUID.randomUUID();
        this.clientsMap.put(randomUUID,webSocket);
        return randomUUID.toString();
    }

    @Override
    public void sendMessageLoadData(Address dbServer, String uuid) {
        MessageLoadData messageLoadData = new MessageLoadData(getAddress(),dbServer,uuid);
        messageSystemContext.getWorker().send(messageLoadData.getJsonObject());
    }

    public void sendSaveData(String data) {
        MessageSaveData messageSaveData = new MessageSaveData(this.getAddress(),  messageSystemContext.getDbServerAddress(), data);
        messageSystemContext.getWorker().send(messageSaveData.getJsonObject());
    }

    @Override
    public void sendDataClient(String uuid, String data) {
        if (uuid.equals(ALL_CLIENT)) {
            for (ServiceWebSocket item : clientsMap.values()) {
                try {
                    if (item.getSession().isOpen()) {
                        item.getSession().getRemote().sendString(data);
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        } else {
            try {
                if (clientsMap.get(UUID.fromString(uuid)) != null && clientsMap.get(UUID.fromString(uuid)).getSession().isOpen()) {
                    clientsMap.get(UUID.fromString(uuid)).getSession().getRemote().sendString(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Address getAddress() {
        return  messageSystemContext.getFrontendAddress();
    }

    public void sendUserList(String uuid) {
        MessageLoadData messageLoadData = new MessageLoadData(this.getAddress(),  messageSystemContext.getDbServerAddress(), uuid);
        messageSystemContext.getWorker().send(messageLoadData.getJsonObject());
    }

    public void removeClient(String uuid) {
        this.clientsMap.remove(uuid);
    }

}
