package ru.otus.hw16messageserver.server.messageserver.messagesystem;

import ru.otus.hw16messageserver.server.ProcessRunner;

import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class MessageSystemContext  {

    private static final String HOST = "localhost";

    private static final int MESSAGESERVER_PORT = 8091;
    private static final String MESSAGESERVER_START_COMMAND = "java -jar ../MessageServer/target/messageserver-1.0.jar";

    private static final int DBSERVER_PORT = 8092;
    private static final String DBSERVER_START_COMMAND = "java -jar ../DBServer/target/dbserver.jar";

    private static final int FRONTEND_PORT = 8093;
    private static final String FRONTEND_START_COMMAND = "java -jar ../Frontend/target/frontend.jar";

    private Address dbServiceAddress;
    private Address frontendAddress;
    private MessageSystemSocketServer messageSystem;

    private Map<Address, SocketWorker> messagesMap;

    public MessageSystemContext(MessageSystemSocketServer messageSystem, Address dbServiceAddress, Address frontendAddress) {
        this.messageSystem = messageSystem;
        this.dbServiceAddress = dbServiceAddress;
        this.frontendAddress = frontendAddress;

        this.messageSystem.dbServerAddress = dbServiceAddress;
        this.messageSystem.frontAddress = frontendAddress;

        this.messageSystem.start();


        //try {
            processRun(FRONTEND_START_COMMAND, String.valueOf(FRONTEND_PORT)/*,socketWorkerFontend*//*jsonObject.toString()*/);
            processRun(DBSERVER_START_COMMAND, String.valueOf(DBSERVER_PORT)/*,socketWorkerDBService*//*jsonObject.toString()*/);
/*
            SocketWorker frontendWorker = new SocketWorker(new Socket("localhost",frontendAddress.getPort()));
            frontendWorker.init();
            this.messageSystem.socketClients.put(frontendAddress,frontendWorker);

            SocketWorker dbserverWorker = new SocketWorker(new Socket("localhost",dbServiceAddress.getPort()));
            dbserverWorker.init();
            this.messageSystem.socketClients.put(dbServiceAddress,dbserverWorker);*/
/*
        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }


    private void processRun(String cmd, String params/*, SocketWorker worker*/) {
        try {
            //logger.info("Process starting...");
            ProcessRunner processRunner = new ProcessRunner();
            processRunner.start(cmd + " " + params);
            //worker = new SocketWorker(new Socket(HOST, Integer.valueOf(params)));
            //worker.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setDBServiceAddress(Address dbServiceAddress) {
        this.dbServiceAddress = dbServiceAddress;
    }

    public Address getDbServiceAddress() {
        return dbServiceAddress;
    }

    public void setFrontendServiceAddress(Address frontendAddress) {
        this.frontendAddress = frontendAddress;
    }

    public Address getFrontend() {
        return frontendAddress;
    }

    public MessageSystemSocketServer getMessageSystem() {
        return this.messageSystem;
    }
}
