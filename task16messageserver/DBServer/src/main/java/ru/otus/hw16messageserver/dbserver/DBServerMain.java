package ru.otus.hw16messageserver.dbserver;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.hw16messageserver.dbserver.hibernate.DBService;
import ru.otus.hw16messageserver.dbserver.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw16messageserver.dbserver.hibernate.dbservice.DBServiceHibernateImpl;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.SocketWorker;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.MessageLoadData;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.MessageToClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class DBServerMain {

    private static final Logger logger = Logger.getLogger(DBServerMain.class.getName());
    private static final int THREADS_NUMBER = 1;
    private final ExecutorService executor;
    private SocketWorker socketWorker = null;
    private DBServiceHibernateImpl dbService;

    private int port;
    public static void main(String[] args) throws IOException {

        /*
        Address frontend = new Address("localhost",8093);
        Address dbserver = new Address("localhost",8092);
        Message msg = new MessageToFrontend(frontend,dbserver,"the force awakens");
        */

        int port = Integer.parseInt(args[0]);
        //DBService dbService = DBHelper.createDBService(port);
        //logger.info("DBServerMain started on port: " + port);

        DBServerMain dbServerMain =  new DBServerMain(port);
        dbServerMain.start();

        /*
        SocketWorker socketWorker = new SocketWorker(new Socket("localhost",8091));
        socketWorker.init();
        */
    }

    public DBServerMain(int port) {
        this.port = port;
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        dbService = (DBServiceHibernateImpl) DBHelper.createDBService(port);
    }

    private void start() throws IOException {
        logger.info("DBServerMain SocketWorker try start");
        socketWorker = new SocketWorker(new Socket("localhost",8091));
        logger.info("DBServerMain SocketWorker started");
        socketWorker.init();
        logger.info("DBServerMain SocketWorker init");
        executor.submit(this::processing);

    /*    try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true/*!executor.isShutdown()*//*) {
                Socket socket = serverSocket.accept(); //blocks
                System.out.println("MessageServer get message!");
                executor.execute(() -> receiveMessage(socket));*/
                /*

                try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                    String inputLine;
                    StringBuilder stringBuilder = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) { //blocks
                        logger.info("MessageServer started while");
                        stringBuilder.append(inputLine);
                        if (inputLine.isEmpty()) { //empty line is the end of the message
                            logger.info("MessageServer get message: " + stringBuilder.toString());
                        }
                    }
                }
                ;
                */

                /*
                SocketMsgWorker worker = newSocketMsgWorker(socket);
                worker.init();
                workers .add(worker);
                */
          //  }
        //}
    }


    public void processing() {
        while (true) {

            //for (Map.Entry<Address, SocketWorker> socketClient : socketWorker.()) {
            logger.info("DBServer processing");
            String messageBody = null;
            try {
                messageBody = socketWorker.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("DBServer processing : messageBody get");
            while (messageBody != null) {
                try {
                    logger.info("DBServer get message : " + messageBody);
                    JSONParser jsonParser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(messageBody);
                    String className = (String) jsonObject.get("className");
                    String gsonData = (String) jsonObject.get("data");
                    Class<?> msgClass = Class.forName(className);
                    var messageObj = new Gson().fromJson(gsonData, msgClass);
                    if (messageObj instanceof MessageLoadData) {

                        Gson gson = createGsonWithFilter();
                        List<UserDataSetHibernate> dbUserList = dbService.userGetAllList();
                        MessageToClient message = new MessageToClient();
                        message.data = gson.toJson(dbUserList);
                        message.uuid = UUID.fromString(((MessageLoadData) messageObj).getData());
                        socketWorker.send(message.getJsonObject());

                        //this.addClient(message.());
                        //Message messageLodaData = new MessageLoadData(message.getTo(),message.getTo(),message.getData());
                        //logger.info(messageLodaData.getJsonObject());
                        //socketWorker.send(messageLodaData.getJsonObject());
                        //logger.info("Message from " + ((Message) messageObj).getFrom() + " to " + ((Message) messageObj).getTo());
                    }
                        /*if (messageObj instanceof MessageToClient) {
                            sendDataClient(((MessageToClient) messageObj).uuid, ((MessageToClient) messageObj).data);
                        }
                        //socketClient.send(messageBody);*/
                    messageBody = socketWorker.take();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        /*
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.info(e.toString());
            }*/
    }

    private Gson createGsonWithFilter() {
        return new GsonBuilder().addSerializationExclusionStrategy(new ExclusionStrategy() {
            @Override
            public boolean shouldSkipField(FieldAttributes fieldAttributes) {
                boolean shouldSkipField = fieldAttributes.getDeclaredClass().equals(UserDataSetHibernate.class);
                return shouldSkipField;
            }

            @Override
            public boolean shouldSkipClass(Class<?> aClass) {
                return false;
            }
        }).create();
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

                    String json = stringBuilder.toString();
                    Msg msg = getMsgFromJSON(json);
                    input.add(msg);
                    stringBuilder = new StringBuilder();
                    //clients.add(UUID.fromString(stringBuilder.toString()));
                    logger.info("FrontendServiceImpl get message: " + stringBuilder.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

}
