package ru.otus.hw16messageserver.server.messageserver.messagesystem;

import ru.otus.hw16messageserver.server.messageserver.messagesystem.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Logger;

public class MessageSystemImpl implements MessageSystem {

    private final Logger logger = Logger.getLogger(MessageSystemImpl.class.getName());

    private static final int THREADS_NUMBER = 1;

    private final Map<Address, LinkedBlockingQueue<Message>> messagesMap;
    private final List<SocketWorker> workers;

    private int port;

    private final ExecutorService executor;
    private SocketWorker worker;

    public MessageSystemImpl(int port) {
        this.messagesMap = new HashMap<>();
        this.workers = new ArrayList<>();
        this.port = port;
        executor = Executors.newFixedThreadPool(THREADS_NUMBER);
    }

    public void start() {
        executor.submit(this::processing);
            executor.submit(() -> {
                try (ServerSocket serverSocket = new ServerSocket(port)) {
                    Socket socket = serverSocket.accept(); //blocks
                    worker = new SocketWorker(socket);
                    worker.init();
                    logger.info("new worker! port: " + socket.getChannel());
                    workers.add(worker);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

    }



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
                //workers.remove();
            }
        } /*catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public void processing() {
        /*
        while (true) {
            for (SocketWorker worker : workers) {
                Message message = worker.pool();
                while (message != null) {
                    logger.info(message);
                    worker.send(message);
                    message = worker.pool();
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.log(Level.SEVERE, e.toString());
            }
        }
        */

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
        workers.add(thread);*/
    }

    public void sendMessage(Message message) {
        messagesMap.get(message.getTo()).add(message);
    }
}
