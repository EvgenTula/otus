package ru.otus.hw16messageserver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SocketWorker {
    private static final Logger logger = Logger.getLogger(SocketWorker.class.getName());
    private static final int WORKERS_COUNT = 2;

    private final BlockingQueue<String> output = new LinkedBlockingQueue<>();
    private final BlockingQueue<String> input = new LinkedBlockingQueue<>();

    private final ExecutorService executor;
    private final Socket socket;

    public SocketWorker(Socket socket/*, HashMap<String, DBServiceWebSocket> */) {
        this.socket = socket;
        this.executor = Executors.newFixedThreadPool(WORKERS_COUNT);
    }

    public void send(String data) {
        output.add(data);
    }

    public String pool() {
        return input.poll();
    }

    public String  take() throws InterruptedException {
        return input.take();
    }

    public void close() {
        executor.shutdown();
    }

    public void init() {
        executor.execute(this::sendMessage);
        executor.execute(this::receiveMessage);
    }

    private void sendMessage() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            while (socket.isConnected()) {
                String data = output.take(); //blocks
                out.println(data);
                out.println();//line with json + an empty line
                logger.info("ServerMain send data");
            }
        } catch (InterruptedException | IOException e) {
            logger.log(Level.SEVERE, e.getMessage());
        }
    }

    private void receiveMessage() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String inputLine;
            StringBuilder stringBuilder = new StringBuilder();
            while ((inputLine = in.readLine()) != null) { //blocks
                stringBuilder.append(inputLine);
                if (inputLine.isEmpty()) { //empty line is the end of the message
                    String data = stringBuilder.toString();
                    input.add(data);
                    //stringBuilder = new StringBuilder();
                    logger.info("ServerMain get data: " + data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }
}
