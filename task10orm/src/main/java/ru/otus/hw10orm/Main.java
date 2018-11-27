package ru.otus.hw10orm;

import ru.otus.hw10orm.dataset.UserDataSet;
import ru.otus.hw10orm.dbservice.*;

import java.util.ArrayList;
import java.util.List;

/*
Здравствуйте. Хорошая работа. Есть небольшие замечания:
1. Название DBService намекает, что это компонент для работы с БД в целом, а не только UserDataSet.
Соответственно имена полей тут должны быть не просто load, save и т.д., а loadUser, saveUser или что-то подобное.
По этой же причине addClass должен смотреть наружу и вызываться извне.

2. Очень круто, что у вас используется PreparedStatment и что вы закешировали поля класса.
В этой ситуации было бы классно еще закешировать запросы.
Сейчас вы их делаете каждый вызов метода, а можно сделать один раз в addClass
*/

public class Main {

    public static void main(String[] args) {
        DBService dbService = new DBServiceImpl();
        dbService.addClass(UserDataSet.class,"user");
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