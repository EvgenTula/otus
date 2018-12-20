package ru.otus.hw12webserver;

import ru.otus.hw12webserver.hibernate.DBService;
import ru.otus.hw12webserver.hibernate.config.ConfigurationHibernate;
import ru.otus.hw12webserver.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw12webserver.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw12webserver.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw12webserver.hibernate.dbservice.DBServiceHibernateImpl;

import java.util.ArrayList;
import java.util.List;

public class DBManager {

    private DBService dbService = null;
    public DBService createDBService() {
        dbService = new DBServiceHibernateImpl(new ConfigurationHibernate());
        List<PhoneDataSetHibernate> phones = new ArrayList<>();
        phones.add(new PhoneDataSetHibernate("1111"));
        phones.add(new PhoneDataSetHibernate("2222"));
        phones.add(new PhoneDataSetHibernate("3333"));
        dbService.save(new UserDataSetHibernate("test1 hibernate", 1, new AddressDataSetHibernate("test1 address hibernate"), phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("4444"));
        phones.add(new PhoneDataSetHibernate("5555"));
        phones.add(new PhoneDataSetHibernate("6666"));
        dbService.save(new UserDataSetHibernate("test2 hibernate", 1, new AddressDataSetHibernate("test2 address hibernate"), phones));

        phones.clear();
        phones.add(new PhoneDataSetHibernate("7777"));
        phones.add(new PhoneDataSetHibernate("8888"));
        phones.add(new PhoneDataSetHibernate("9999"));
        dbService.save(new UserDataSetHibernate("test3 hibernate", 1, new AddressDataSetHibernate("test3 address hibernate"), phones));

        return dbService;
    }
}
