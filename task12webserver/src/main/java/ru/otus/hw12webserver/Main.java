package ru.otus.hw12webserver;

import org.eclipse.jetty.server.Server;
import ru.otus.hw12webserver.hibernate.DBService;

public class Main {

    public static void main(String[] args) throws Exception {

        DBManager dbManager = new DBManager();
        DBService dbService = dbManager.createDBService();
        ServerManager serverManager = new ServerManager();
        Server server = serverManager.createServer(dbService, 8090);
        server.start();
        server.join();
    }
}
