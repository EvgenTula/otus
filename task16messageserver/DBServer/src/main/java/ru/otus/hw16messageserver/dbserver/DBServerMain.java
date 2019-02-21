package ru.otus.hw16messageserver.dbserver;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import ru.otus.hw16messageserver.dbserver.dbservice.DBHelper;
import ru.otus.hw16messageserver.dbserver.dbservice.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw16messageserver.dbserver.dbservice.hibernate.dbservice.DBServiceHibernateImpl;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.DBServer;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.SocketWorker;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;
import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.frontend.MessageToClient;

import java.io.IOException;


import java.net.Socket;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class DBServerMain implements DBServer {

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
    }

    public DBServerMain(int port) {
        this.port = port;
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        dbService = (DBServiceHibernateImpl) DBHelper.createDBService(port);
    }

    public void start() {
        try {

            logger.info("DBServerMain SocketWorker try start");
            socketWorker = new SocketWorker(new Socket("localhost",8091));
        logger.info("DBServerMain SocketWorker started");
        socketWorker.init();
        logger.info("DBServerMain SocketWorker init");
        executor.submit(this::processing);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int saveData(String data) {
        //dbService.save();
        return 0;
    }

    @Override
    public String loadUserByid(int id) {
        return null;
    }

    @Override
    public String loadUserList() {
        Gson gson = createGsonWithFilter();
        List<UserDataSetHibernate> dbUserList = dbService.userGetAllList();
        return gson.toJson(dbUserList);
    }

    @Override
    public void sendDataToFrontend(Address frontend,String uuid, String data) {
        MessageToClient messageToClient = new MessageToClient(getAddress(),frontend, data, uuid);
        socketWorker.send(messageToClient.getJsonObject());
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
                    JSONObject jsonObject = (JSONObject) jsonParser.parse(messageBody.toString());
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

    @Override
    public Address getAddress() {
        return new Address("localhost",8092);
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
