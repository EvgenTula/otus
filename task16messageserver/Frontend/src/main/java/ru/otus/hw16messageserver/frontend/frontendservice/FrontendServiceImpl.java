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
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.FrontendService;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Member;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.SocketWorker;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.*;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.dbservice.MessageLoadData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class FrontendServiceImpl implements FrontendService {

    private static final Logger logger = Logger.getLogger(FrontendServiceImpl.class.getName());

    private int port;

    /*
    private final static String ALL_CLIENT = "ALL_CLIENT";
    private Address address;
    private MessageSystemContext messageSystemContext;
    private ConcurrentHashMap<UUID, DBServiceWebSocket> clientsMap;
    */
    List<UUID> clients;
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
        this.port = port;
        this.clients = new ArrayList<>();
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
            logger.info("Frontend SocketWorker started");
            socketWorker.init();
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
                        if (messageObj instanceof Message) {
                            ((Message) messageObj).exec(this);
                        }
                        /*
                        if (messageObj instanceof MessageClientConnect) {
                            MessageClientConnect message = (MessageClientConnect) messageObj;
                            this.addClient(message.getData());

                            Message messageLodaData = new MessageLoadData(message.getTo(),message.getFrom(),message.getData());

                            logger.info(messageLodaData.getJsonObject());

                            socketWorker.send(messageLodaData.getJsonObject());

                            logger.info("Froneend send data!");
                        }
                        */
                        /*if (messageObj instanceof MessageToClient) {
                            sendDataClient(((MessageToClient) messageObj).uuid, ((MessageToClient) messageObj).data);
                        }
                        //socketClient.send(messageBody);*/
                        messageBody = socketWorker.take();
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





        private void receiveMessage(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
                //System.out.println("Message received: " + inputLine);
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(stringBuilder.toString());
                    String className = (String) jsonObject.get("className");
                    String gsonData = (String) jsonObject.get("data");
                    Class<?> msgClass = Class.forName(className);
                    Message message = (Message) new Gson().fromJson(gsonData, msgClass);
                    message.exec(this);
                    //logger.info("Class.forName :" + msgClass.getName());
                    //logger.info("MessageToFrontend1.class.getName(): " + MessageToFrontend.class.getName());
                    //MessageToFrontend message = (MessageToFrontend)new Gson().fromJson(gsonData, msgClass);
                    //()new Gson().fromJson(gsonData, msgClass);
                    //return (Msg) new Gson().fromJson(json, msgClass);
                    //clients.add(UUID.fromString(stringBuilder.toString()));
                    logger.info("FrontendServiceImpl get message: " + message.getData());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addClient(String uuid) {
        clients.add(UUID.fromString(uuid));
    }

    @Override
    public void sendMessageLoadData(Address dbServer, String uuid) {
        MessageLoadData messageLoadData = new MessageLoadData(getAddress(),dbServer,uuid);
        socketWorker.send(messageLoadData.getJsonObject());
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
        MessageToWebsocket messageToWebsocket = new MessageToWebsocket();
        messageToWebsocket.data = data;
        messageToWebsocket.uuid = UUID.fromString(uuid);
        socketWorker.send(messageToWebsocket.getJsonObject());

        /*
        Gson gson = createGsonWithFilter();
        List<UserDataSetHibernate> dbUserList = dbService.userGetAllList();
        MessageToClient message = new MessageToClient();
        message.data = gson.toJson(dbUserList);
        message.uuid = UUID.fromString(((MessageLoadData) messageObj).getData());
        socketWorker.send(message.getJsonObject());


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
        */
    }



    @Override
    public Address getAddress() {
        return new Address("localhost",8093);
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
    public String addClient(DBServiceWebSocket webSocket) {
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
