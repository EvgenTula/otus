package ru.otus.hw11hibernate.orm.config;

import ru.otus.hw11hibernate.orm.dataset.AddressDataSetOrm;
import ru.otus.hw11hibernate.orm.dataset.PhoneDataSetOrm;
import ru.otus.hw11hibernate.orm.dataset.UserDataSetOrm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfigurationOrm {

    private Connection connection;
    private List<DataSetConfiguration> configurationList;

    public ConfigurationOrm() {
        try {
            Class.forName("org.h2.Driver");
            String url = "jdbc:h2:~/test";
            String login = "sa";
            String pswd = "sa";
            connection = DriverManager.getConnection(url,login, pswd);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        configurationList = new ArrayList<>();

        configurationList.add(
                new DataSetConfiguration(
                        AddressDataSetOrm.class, "address","(id bigint auto_increment, street varchar(255), primary key (id))"));
        configurationList.add(
                new DataSetConfiguration(
                        UserDataSetOrm.class,
                        "user",
                        "(id bigint auto_increment, name varchar(255), age int, address_id bigint, foreign key (address_id) references address(id), primary key (id))"));
        configurationList.add(
                new DataSetConfiguration(
                        PhoneDataSetOrm.class, "phone","(id bigint auto_increment, number varchar(255), " +
                        "userdataset_id bigint, foreign key (userdataset_id) references user(id), primary key (id))"));

    }
    public Connection getConnection() {
        return this.connection;
    }

    public List<DataSetConfiguration> getConfigurationList() {
        return this.configurationList;
    }
}
