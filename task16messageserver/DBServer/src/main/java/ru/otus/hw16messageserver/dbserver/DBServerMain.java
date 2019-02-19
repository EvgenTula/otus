package ru.otus.hw16messageserver.dbserver;

import ru.otus.hw16messageserver.dbserver.hibernate.DBService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class DBServerMain {

    private static final Logger logger = Logger.getLogger(DBServerMain.class.getName());
    private static final int THREADS_NUMBER = 1;
    private final ExecutorService executor;


    private int port;
    public static void main(String[] args) throws IOException {
        int port = Integer.parseInt(args[0]);
        //DBService dbService = DBHelper.createDBService(port);
        logger.info("DBServerMain started on port: " + port);

        DBServerMain dbServerMain =  new DBServerMain(port);
        dbServerMain.start();
    }

    public DBServerMain(int port) {
        this.port = port;
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    private void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true/*!executor.isShutdown()*/) {
                Socket socket = serverSocket.accept(); //blocks
                System.out.println("MessageServer get message!");
                executor.execute(() -> receiveMessage(socket));
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
                    /*
                    String json = stringBuilder.toString();
                    Msg msg = getMsgFromJSON(json);
                    input.add(msg);
                    stringBuilder = new StringBuilder();*/
                    //clients.add(UUID.fromString(stringBuilder.toString()));
                    logger.info("FrontendServiceImpl get message: " + stringBuilder.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
