package ru.otus.hw10orm.dbservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionHelper {


    static Connection getConnection() {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:~/test";
            String login = "sa";
            String pswd = "sa";
            return DriverManager.getConnection(url,login, pswd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
