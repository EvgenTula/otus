package ru.otus.hw16messageserver.frontend.frontendservice;
/*
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.service.MessageLoadData;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.service.MessageSaveData;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;*/

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.hw16messageserver.frontend.websocket.ServiceWebSocket;
import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.messageserver.messagesystem.SocketWorker;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.*;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.dbservice.MessageLoadData;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class FrontendServiceImpl implements FrontendService {

    private static final Logger logger = Logger.getLogger(FrontendServiceImpl.class.getName());

    private final static String ALL_CLIENT = "ALL_CLIENT";

    private int port;
    private Address currentAddress;
    private Address dbServerAddress;

    /*
    private final static String ALL_CLIENT = "ALL_CLIENT";
    private Address address;
    private MessageSystemContext messageSystemContext;
    private ConcurrentHashMap<UUID, ServiceWebSocket> clientsMap;
    */
    private ConcurrentHashMap<UUID, ServiceWebSocket> clientsMap;
    /*
    public FrontendServiceImpl(MessageSystemContext messageSystemContext,Address address) {
        this.messageSystemContext = messageSystemContext;
        this.address = address;
        this.clientsMap = new ConcurrentHashMap<>();
    }
    */
    private static final int THREADS_NUMBER = 1;

    private final ExecutorService executor;

    private SocketWorker socketWorker = null;
    //private int

    public FrontendServiceImpl(int port) {
        this.dbServerAddress = new Address("localhost",8092);
        this.currentAddress = new Address("localhost", port);
        this.port = port;
        this.clientsMap = new ConcurrentHashMap<>();
        this.executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        /*
        this.messageSystemContext = messageSystemContext;
        this.address = address;
        this.clientsMap = new ConcurrentHashMap<>();
        */
    }


    @Override
    public void start() {
        logger.info("Frontend SocketWorker try start");
        try {
            socketWorker = new SocketWorker(new Socket("localhost",8091));
            socketWorker.init();
            MessageToRegisterSocketClient messageToRegister = new MessageToRegisterSocketClient(this.getAddress(), null, "");
            socketWorker.send(messageToRegister.getJsonObject());
            logger.info("Frontend SocketWorker init");
            executor.submit(this::processing);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void processing() {
        while (true) {
            logger.info("Frontend processing");
            String messageBody = null;
            try {
                messageBody = socketWorker.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Frontend processing : messageBody get");
                while (messageBody != null) {
                    try {
                        logger.info("Frontend get message :" + messageBody);
                        JSONParser jsonParser = new JSONParser();

                        JSONObject jsonObject = (JSONObject) jsonParser.parse(messageBody);
                        String className = (String) jsonObject.get("className");
                        String gsonData = (String) jsonObject.get("data");
                        Class<?> msgClass = Class.forName(className);
                        var messageObj = new Gson().fromJson(gsonData, msgClass);
                        logger.info("Frontend get message :" + messageObj);
                        if (messageObj instanceof Message) {
                            logger.info("Frontend get message :" + messageObj + " exec");
                            ((Message) messageObj).exec(this);
                        }
                        messageBody = socketWorker.take();
                   } catch (ParseException e) {
                        e.printStackTrace();
                        logger.info("Frontend ParseException: " +messageBody);
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
        socketWorker.send(messageLoadData.getJsonObject());
    }

    @Override
    public void sendSaveData(String data) {

    }


    /*
    @Override
    public Address getAddress() {
        return this.address;
    }*/

    /*
    @Override
    public MessageSystemImpl getMessageSystem() {
        return messageSystemContext.getMessageSystem();
    }
*/

    @Override
    public void sendDataClient(String uuid, String data) {
        if (uuid.equals(ALL_CLIENT)) {
            for (ServiceWebSocket item : clientsMap.values()) {
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
    public Address getAddress() {
        return this.currentAddress;
    }


    public void sendUserList(String uuid) {
        MessageLoadData messageLoadData = new MessageLoadData(this.getAddress(), dbServerAddress, uuid);
        socketWorker.send(messageLoadData.getJsonObject());
    }


    /*
    @Override
    public void sendSaveUserMessage(String data) {
        messageSystemContext.getMessageSystem().sendMessage(
                new MessageSaveData(this.getAddress(),messageSystemContext.getDbServiceAddress(),data)
        );
    }
*/
    /*
    @Override
    public void sendGetUsersListMessage(String uuid) {
        this.messageSystemContext.getMessageSystem().sendMessage(
                new MessageLoadData(this.getAddress(),
                                    messageSystemContext.getDbServiceAddress(),uuid));
    }
*/
    /*
    @Override
    public String addClient(ServiceWebSocket webSocket) {
        UUID randomUUID = UUID.randomUUID();
        this.clientsMap.put(randomUUID,webSocket);
        return randomUUID.toString();
    }
*/
    /*
    @Override
    public void removeClient(String uuid) {
        this.clientsMap.remove(uuid);
    }
    */
}
