package ru.otus.hw16messageserver;

import org.eclipse.jetty.server.Server;

public class Main {
    public static void main(String[] args) throws Exception {
        Server server = ServerHelper.createServer(8090);
        server.start();
        server.join();
    }
}
