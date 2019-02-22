package ru.otus.hw16messageserver.server.messageserver.messagesystem;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.dbservice.MessageSaveData;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.frontend.MessageClientConnect;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.MessageToWebsocket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;



public class MessageSystemSocketServer {

    private final Logger logger = Logger.getLogger(MessageSystemSocketServer.class.getName());

    private static final int THREADS_NUMBER = 2;

    private final Map<Socket, LinkedBlockingQueue<Message>> messagesMap;
    //private ConcurrentHashMap<UUID, DBServiceWebSocket> clientsMap;
    public final ConcurrentHashMap<Address, SocketWorker> socketClients;

    private int port;

    private final ExecutorService executor;
    private SocketWorker worker;

    public SocketWorker workerFront;

    public Address frontAddress;
    public Address dbServerAddress;

    public SocketWorker workerDBServer;

    public MessageSystemSocketServer(int port) {
        //this.clientsMap = new ConcurrentHashMap<>();
        this.messagesMap = new HashMap<>();
        this.socketClients = new ConcurrentHashMap<>();
        this.port = port;
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    public void start() {

        //executor.submit(this::processing);

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket socket = serverSocket.accept(); //blocks
        worker = new SocketWorker(socket);
        worker.init();
        /*
        SocketWorker frontendWorker = new SocketWorker(new Socket("localhost",frontendAddress.getPort()));
            frontendWorker.init();
            this.messageSystem.socketClients.put(frontendAddress,frontendWorker);

            SocketWorker dbserverWorker = new SocketWorker(new Socket("localhost",dbServiceAddress.getPort()));
            dbserverWorker.init();
            this.messageSystem.socketClients.put(dbServiceAddress,dbserverWorker);


        */
        executor.submit(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (!executor.isShutdown()) {
                    Socket socket = serverSocket.accept(); //blocks
                    worker = new SocketWorker(socket);
                    logger.info("new worker : " + socket.toString());
                    worker.init();
                    logger.info("new worker : " + socket.toString() + " init()");
                    if (!socketClients.containsKey(frontAddress)) {
                        socketClients.put(frontAddress, worker);
                        logger.info("new worker : is front!");
                    } else {
                        socketClients.put(dbServerAddress, worker);
                        logger.info("new worker : is dbserver!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        logger.info("Message server is started!");
    }


/*
    private void receiveMessage(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
                //System.out.println("Message received: " + inputLine);
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    logger.info("MessageServer get message: " + stringBuilder.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (socket.isClosed())
            {
                //socketClients.remove();
            }
        }
    }
*/

    public void processing()  {
        while (true) {
            for (Map.Entry<Address,SocketWorker> socketClient : socketClients.entrySet()) {
                String messageBody = socketClient.getValue().pool();

                while (messageBody != null) {
                    try {
                        logger.info("MessageServer get data :" + messageBody);
                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObject = (JSONObject)jsonParser.parse(messageBody);
                        String className = (String) jsonObject.get("className");
                        String gsonData = (String) jsonObject.get("data");
                        Class<?> msgClass = Class.forName(className);

                        var messageObj = new Gson().fromJson(gsonData, msgClass);
                        logger.info("MessageServer get gata > className  : " + messageObj.getClass().getName() + " ; data : " + gsonData);

                        if (Message.class.isAssignableFrom(msgClass)) {
                            Message message = (Message) messageObj;
                            socketClients.get(message.getTo()).send(message.getJsonObject());
                            logger.info("Message from " + ((Message) messageObj).getFrom() + " to " + ((Message) messageObj).getTo());
                        }
                        if (MessageToWebsocket.class.isAssignableFrom(msgClass)) {
                            sendDataClient(((MessageToWebsocket) messageObj).uuid,((MessageToWebsocket) messageObj).data);
                        }
                        //socketClient.send(messageBody);
                        messageBody = socketClient.getValue().pool();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.info(e.toString());
            }
        }


        /*
        this.messagesMap.put(sender.getAddress(), new LinkedBlockingQueue<>());
        Thread thread = new Thread(() -> {
            while (true) {
                LinkedBlockingQueue<Message> queue = messagesMap.get(sender.getAddress());
                try {
                    Message message = queue.take();
                    message.exec(sender);
                } catch (InterruptedException e) {
                    return;
                }
            }
        });
        thread.setName(String.valueOf(sender.getAddress().getPort()));
        thread.start();
        socketClients.add(thread);*/
    }

    public void sendMessage(Message message) {
        socketClients.get(message.getTo()).send(message.getJsonObject());
        //messagesMap.get(message.getTo()).add(message);
    }

    public void sendDataClient(UUID uuid,String data) {
        /*
        try {
            this.clientsMap.get(uuid).getSession().getRemote().sendString(data);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
/*
    public String addClient(DBServiceWebSocket webSocket) {
        UUID randomUUID = UUID.randomUUID();
        this.clientsMap.put(randomUUID,webSocket);
        Message message = new MessageClientConnect(
                dbServerAddress,
                frontAddress,
                randomUUID.toString());
        sendMessage(message);

        return randomUUID.toString();
    }*/

    public void removeClient(String uuid) {
        //this.clientsMap.remove(UUID.fromString(uuid));
    }

    public void saveData(String data) {
        //TODO: доделать!!!
        Message message = new MessageSaveData(dbServerAddress, frontAddress, data);
        sendMessage(message);
    }
}
