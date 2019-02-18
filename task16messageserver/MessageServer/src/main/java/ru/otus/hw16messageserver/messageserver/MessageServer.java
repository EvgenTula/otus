package ru.otus.hw16messageserver.messageserver;

import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.servermain.ProcessRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MessageServer {

    private static final int DBSERVER_PORT = 8092;
    private static final String DBSERVER_START_COMMAND = "java -jar ../L16.1.2-client/target/client.jar" + DBSERVER_PORT;

    private static final int FRONTEND_PORT = 8093;
    private static final String FRONTEND_START_COMMAND = "java -jar ../Frontend/target/frontend-1.0.jar " + FRONTEND_PORT;

    private static final Logger logger = Logger.getLogger(MessageServer.class.getName());
    private static final int THREADS_NUMBER = 1;
    private static int port;

    MessageSystemContext messageSystemContext;
    private final ExecutorService executor;

    public static void main(String[] args) throws Exception {
        port = Integer.parseInt(args[0]);
        MessageServer messageServer = new MessageServer(port);
        messageServer.start();
    }

    public MessageServer(int port) {
        logger.info("MessageServer started on port: " + port);
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        messageSystemContext = new MessageSystemContext(new MessageSystemImpl());
        Address dbServerAddress = new Address(1);//"dbServiceAddress");
        Address frontendAddress = new Address(2);//"frontendAddress");
        messageSystemContext.setDBServiceAddress(dbServerAddress);
        messageSystemContext.setFrontendServiceAddress(frontendAddress);


        ProcessRunner processRunner = new ProcessRunner();

        try {
            logger.log(Level.INFO, "DBServer starting...");
            processRunner.start(DBSERVER_START_COMMAND);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            logger.log(Level.INFO, "Frontend starting...");
            processRunner.start(FRONTEND_START_COMMAND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        //executor.submit(this::echo);

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

    /*
        private void sendMessage(Socket socket) {
            try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                while (socket.isConnected()) {
                    Msg msg = output.take(); //blocks
                    String json = new Gson().toJson(msg);
                    out.println(json);
                    out.println();//line with json + an empty line
                }
            } catch (InterruptedException | IOException e) {
                logger.log(Level.SEVERE, e.getMessage());
            }
        }
    */
    private void receiveMessage(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            logger.info("receiveMessage BufferedReader");
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
                logger.info("MessageServer while");
                //System.out.println("Message received: " + inputLine);
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    /*
                    String json = stringBuilder.toString();
                    Msg msg = getMsgFromJSON(json);
                    input.add(msg);
                    stringBuilder = new StringBuilder();*/
                    logger.info("MessageServer get message: " + stringBuilder.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void echo() {
        while (true) {
            /*
            for (MsgWorker worker : workers) {
                Msg msg = worker.pool();
                while (msg != null) {
                    System.out.println("Mirroring the message: " + msg.toString());
                    worker.send(msg);
                    msg = worker.pool();
                }
            }
            try {
                Thread.sleep(MIRROR_DELAY_MS);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }*/
        }
    }
}
