package ru.otus.hw12webserver;

import org.eclipse.jetty.server.Server;
import ru.otus.hw12webserver.hibernate.DBService;

public class Main {

    public static void main(String[] args) throws Exception {
        DBService dbService = DBHelper.createDBService();
        Server server = ServerHelper.createServer(dbService, 8090);
        server.start();
        server.join();
    }
}
