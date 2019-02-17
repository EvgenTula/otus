package ru.otus.hw16messageserver.messageserver;

import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemImpl;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class MessageServer {

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
        logger.info("Server started on port: " + port);
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
        messageSystemContext = new MessageSystemContext(new MessageSystemImpl());
        Address dbServerAddress = new Address(1);//"dbServiceAddress");
        Address frontendAddress = new Address(2);//"frontendAddress");
        messageSystemContext.setDBServiceAddress(dbServerAddress);
        messageSystemContext.setFrontendServiceAddress(frontendAddress);
    }

    public void start() throws Exception {
        //executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(port)) {

            while (true/*!executor.isShutdown()*/) {
                Socket socket = serverSocket.accept(); //blocks
                logger.info("MessageServer get message! " + socket.getLocalPort());
                /*
                SocketMsgWorker worker = newSocketMsgWorker(socket);
                worker.init();
                workers .add(worker);
                */
            }
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
