package ru.otus.hw16messageserver.servermain;

import org.eclipse.jetty.server.Server;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        Server server = ServerHelper.createServer(8090);
        server.start();
        server.join();
    }
}
