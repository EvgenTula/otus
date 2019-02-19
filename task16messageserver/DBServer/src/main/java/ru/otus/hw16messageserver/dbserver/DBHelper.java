package ru.otus.hw16messageserver.dbserver;

import ru.otus.hw16messageserver.dbserver.hibernate.DBService;
import ru.otus.hw16messageserver.dbserver.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw16messageserver.dbserver.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw16messageserver.dbserver.hibernate.config.ConfigurationHibernate;
import ru.otus.hw16messageserver.dbserver.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw16messageserver.dbserver.hibernate.dbservice.DBServiceHibernateImpl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
//import ru.otus.hw16messageserver.messageserver.messagesystem.Address;
//import ru.otus.hw16messageserver.messageserver.messagesystem.MessageSystemContext;

public class DBHelper {

    private static final int THREADS_NUMBER = 1;

    public static DBService createDBService(int port/*MessageSystemContext messageSystemContext, Address address*/) {
        DBService dbService = new DBServiceHibernateImpl(new ConfigurationHibernate()/*, messageSystemContext, address*/);

        AddressDataSetHibernate user1Address = new AddressDataSetHibernate();
        user1Address.setStreet("Street 1");
        UserDataSetHibernate user1 = new UserDataSetHibernate(
                "test1 hibernate",
                1);
        user1.setAddress(user1Address);
        user1.addPhone(new PhoneDataSetHibernate("1111"));
        user1.addPhone(new PhoneDataSetHibernate("2222"));
        user1.addPhone(new PhoneDataSetHibernate("3333"));
        dbService.save(user1);

        AddressDataSetHibernate user2Address = new AddressDataSetHibernate();
        user2Address.setStreet("Street 2");
        UserDataSetHibernate user2 = new UserDataSetHibernate(
                "test2 hibernate",
                2);
        user2.setAddress(user2Address);
        user2.addPhone(new PhoneDataSetHibernate("4444"));
        user2.addPhone(new PhoneDataSetHibernate("5555"));
        user2.addPhone(new PhoneDataSetHibernate("6666"));
        dbService.save(user2);

        AddressDataSetHibernate user3Address = new AddressDataSetHibernate();
        user3Address.setStreet("Street 3");
        UserDataSetHibernate user3 = new UserDataSetHibernate(
                "test3 hibernate",
                3);
        user3.setAddress(user3Address);
        user3.addPhone(new PhoneDataSetHibernate("7777"));
        user3.addPhone(new PhoneDataSetHibernate("8888"));
        user3.addPhone(new PhoneDataSetHibernate("9999"));
        dbService.save(user3);

        return dbService;
    }
}
