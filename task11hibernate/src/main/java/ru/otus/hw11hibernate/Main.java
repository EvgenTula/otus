package ru.otus.hw11hibernate;

import org.hibernate.cfg.Configuration;
import ru.otus.hw11hibernate.hibernate.datasets.AddressDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.PhoneDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.datasets.UserDataSetHibernate;
import ru.otus.hw11hibernate.hibernate.dbservice.DBServiceHibernateImpl;

import ru.otus.hw11hibernate.orm.dataset.AddressDataSetOrm;
import ru.otus.hw11hibernate.orm.dataset.PhoneDataSetOrm;
import ru.otus.hw11hibernate.orm.dataset.UserDataSetOrm;
import ru.otus.hw11hibernate.orm.dbservice.DBServiceOrmImpl;
import ru.otus.hw11hibernate.orm.dbservice.DataSetConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        /*orm*/
        List<DataSetConfiguration> configurationList = new ArrayList<>();
        configurationList.add(
                new DataSetConfiguration(
                        UserDataSetOrm.class,
                        "user",
                        "(id bigint auto_increment, name varchar(255), age int, primary key (id))"));
        configurationList.add(
                new DataSetConfiguration(
                        PhoneDataSetOrm.class, "phone","(id bigint auto_increment, number varchar(255), age int, " +
                        "userdataset_id bigint, foreign key (userdataset_id) references user(id), primary key (id))"));
        configurationList.add(
                new DataSetConfiguration(
                        AddressDataSetOrm.class, "address","(id bigint auto_increment, street varchar(255), primary key (id))"));
        DBService dbServiceOrm = new DBServiceOrmImpl(configurationList);

        /*
        List<PhoneDataSetOrm> phonesOrm = new ArrayList<>();
        phonesOrm.add(new PhoneDataSetOrm("123"));
        phonesOrm.add(new PhoneDataSetOrm("456"));
        phonesOrm.add(new PhoneDataSetOrm("789"));
        */
        List<UserDataSetOrm> userListOrm = new ArrayList<>();
        /*
        userListOrm.add(new UserDataSetOrm(1,"user 1", 18, new AddressDataSetOrm("test 1 orm"), phonesOrm));
        userListOrm.add(new UserDataSetOrm(2,"user 2", 19, new AddressDataSetOrm("test 2 orm"), phonesOrm));
        userListOrm.add(new UserDataSetOrm(3,"user 3", 20, new AddressDataSetOrm("test 3 orm"), phonesOrm));
        */
        userListOrm.add(new UserDataSetOrm(1,"user 1", 18));
        userListOrm.add(new UserDataSetOrm(2,"user 2", 19));
        userListOrm.add(new UserDataSetOrm(3,"user 3", 20));
        for (UserDataSetOrm item : userListOrm) {
            dbServiceOrm.save(item);
        }

        userListOrm.clear();
        UserDataSetOrm loadUser = dbServiceOrm.load(1, UserDataSetOrm.class);
        System.out.println(loadUser.toString());

        /*hibernate*/
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserDataSetHibernate.class);
        configuration.addAnnotatedClass(AddressDataSetHibernate.class);
        configuration.addAnnotatedClass(PhoneDataSetHibernate.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:~/test");
        configuration.setProperty("hibernate.connection.username", "sa");
        configuration.setProperty("hibernate.connection.password", "sa");

        configuration.setProperty("hibernate.show_sql", "false");
        configuration.setProperty("hibernate.generate_statistics", "false");
        configuration.setProperty("hibernate.use_sql_comments", "false");


        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        configuration.setProperty("hibernate.connection.useSSL", "false");
        configuration.setProperty("hibernate.enable_lazy_load_no_trans", "true");

        DBService dbService = new DBServiceHibernateImpl(configuration);
        List<PhoneDataSetHibernate> phones = new ArrayList<>();
        phones.add(new PhoneDataSetHibernate("123"));
        phones.add(new PhoneDataSetHibernate("456"));
        phones.add(new PhoneDataSetHibernate("789"));
        dbService.save(new UserDataSetHibernate(1,"test1",1,new AddressDataSetHibernate("test1 address"),phones));
        dbService.save(new UserDataSetHibernate(2,"test2",1,new AddressDataSetHibernate("test2 address"),phones));
        dbService.save(new UserDataSetHibernate(3,"test3",1,new AddressDataSetHibernate("test3 address"),phones));
        dbService.save(new UserDataSetHibernate(4,"test4",1,new AddressDataSetHibernate("test4 address"),phones));
        System.out.println(dbService.load(1, UserDataSetHibernate.class).toString());
        System.out.println(dbService.load(1, PhoneDataSetHibernate.class).toString());
        System.out.println(dbService.load(1, AddressDataSetHibernate.class).toString());

        System.out.println(((DBServiceHibernateImpl) dbService).userGetByName("test1").toString());
        List<UserDataSetHibernate> userList = ((DBServiceHibernateImpl) dbService).userGetAllList();
        for (UserDataSetHibernate user: userList) {
            System.out.println(user.toString());
        }
    }
}
