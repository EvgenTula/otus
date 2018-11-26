package ru.otus.hw10orm;

import ru.otus.hw10orm.dataset.UserDataSet;
import ru.otus.hw10orm.dbservice.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();

        List<UserDataSet> newUserList = new ArrayList<>();
        newUserList.add(new UserDataSet(1,"user 1", 18));
        newUserList.add(new UserDataSet(2,"user 2", 19));
        newUserList.add(new UserDataSet(3,"user 3", 20));

        for (UserDataSet item : newUserList) {
            dbService.save(item);
        }

        newUserList.clear();

        UserDataSet loadUser = dbService.load(1,UserDataSet.class);
        System.out.println(loadUser.toString());
    }


}