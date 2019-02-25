package ru.otus.hw16messageserver.messageserver.messagesystem;

import com.google.gson.Gson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.Message;
import ru.otus.hw16messageserver.messageserver.messagesystem.message.MessageToRegisterSocketClient;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;



public class MessageSystemSocketServer {

    private static final int THREADS_NUMBER = 2;

    private final Logger logger = Logger.getLogger(MessageSystemSocketServer.class.getName());
    public final ConcurrentHashMap<Address, SocketWorker> socketClients;
    private final ExecutorService executor;

    private int port;
    private SocketWorker worker;

    public MessageSystemSocketServer(int port) {
        this.socketClients = new ConcurrentHashMap<>();
        this.port = port;
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    public void start() {

        executor.submit(this::processing);

        executor.submit(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (!executor.isShutdown()) {
                    Socket socket = serverSocket.accept(); //blocks
                    worker = new SocketWorker(socket);
                    worker.init();
                    String messageBody = worker.take();
                    if (messageBody != null) {
                        try {
                            JSONParser jsonParser = new JSONParser();
                            JSONObject jsonObject = (JSONObject) jsonParser.parse(messageBody);
                            String className = (String) jsonObject.get("className");
                            String gsonData = (String) jsonObject.get("data");
                            Class<?> msgClass = Class.forName(className);
                            var messageObj = new Gson().fromJson(gsonData, msgClass);
                            if (MessageToRegisterSocketClient.class.isAssignableFrom(msgClass)) {
                                MessageToRegisterSocketClient message = (MessageToRegisterSocketClient) messageObj;
                                if (!socketClients.containsKey(message.getFrom())) {
                                    socketClients.put(message.getFrom(), worker);
                                    logger.info("new worker : is " + message.getFrom().getAddress());
                                }
                            } else {
                                worker.close();
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                            worker.close();
                        } catch (ParseException e) {
                            e.printStackTrace();
                            worker.close();
                        }
                    }
                    worker.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        logger.info("Message server is started!");
    }


    public void processing()  {
        while (true) {
            for (Map.Entry<Address,SocketWorker> socketClient : socketClients.entrySet()) {
                String messageBody = socketClient.getValue().pool();

                while (messageBody != null) {
                    try {
                        JSONParser jsonParser = new JSONParser();
                        JSONObject jsonObject = (JSONObject)jsonParser.parse(messageBody);
                        String className = (String) jsonObject.get("className");
                        String gsonData = (String) jsonObject.get("data");
                        Class<?> msgClass = Class.forName(className);
                        var messageObj = new Gson().fromJson(gsonData, msgClass);
                        if (Message.class.isAssignableFrom(msgClass)) {
                            Message message = (Message) messageObj;
                            socketClients.get(message.getTo()).send(message.getJsonObject());
                        }
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
    }
}
