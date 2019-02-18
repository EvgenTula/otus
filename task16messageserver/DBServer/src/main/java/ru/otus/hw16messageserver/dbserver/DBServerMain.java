package main.java.ru.otus.hw16messageserver.dbserver;

import main.java.ru.otus.hw16messageserver.dbserver.hibernate.DBService;

import java.util.logging.Logger;

public class DBServerMain {

    private static final Logger logger = Logger.getLogger(DBServerMain.class.getName());

    private static int port;
    public static void main(String[] args) {
        port = Integer.parseInt(args[0]);
        DBService dbService = DBHelper.createDBService(port);
        logger.info("DBServerMain started on port: " + port);
        new DBServerMain().start();
    }

    private void start() {

    }

}
