package ru.otus.hw16messageserver.messageserver;

import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;
import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemImpl;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;

public class MessageServer {

    private static final int THREADS_NUMBER = 1;
    private static final int PORT = 5050;

    MessageSystemContext messageSystemContext;
    private final ExecutorService executor;

    public MessageServer() {
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);

        messageSystemContext = new MessageSystemContext(new MessageSystemImpl());
        Address dbServerAddress = new Address("dbServiceAddress");
        Address frontendAddress = new Address("frontendAddress");
        messageSystemContext.setDBServiceAddress(dbServerAddress);
        messageSystemContext.setFrontendServiceAddress(frontendAddress);
    }

    public void start() throws Exception {
        executor.submit(this::echo);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            //logger.info("Server started on port: " + serverSocket.getLocalPort());
            while (!executor.isShutdown()) {
                Socket socket = serverSocket.accept(); //blocks
                /*
                SocketMsgWorker worker = new SocketMsgWorker(socket);
                worker.init();
                workers.add(worker);
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
