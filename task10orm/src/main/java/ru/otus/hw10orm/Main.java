package ru.otus.hw10orm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws SQLException {
        Integer i = 0;
        i = i-- + --i;
        System.out.println(i);
                    /*
        лучше всего в перед тем как использовать сервис в методе init()
        проанализировать все классы на соответствие таблицам. И сохранить результат разбора.
    собственно, как Hibernate и делает
        */

        DBService dbService = new DBService();

        List<UserDataSet> newUserList = new ArrayList<>();
        newUserList.add(new UserDataSet(1,"user 1", 18));
        newUserList.add(new UserDataSet(2,"user 2", 19));
        newUserList.add(new UserDataSet(3,"user 3", 20));

        for (UserDataSet item : newUserList) {
            dbService.(item);
        }

        newUserList.clear();
        UserDataSet loadUser = dbService.load(1,UserDataSet.class);
        System.out.println(loadUser.toString());

        /*
        Connection conn = ConnectionHelper.getConnection();
        Statement st = conn.createStatement();

        st.execute("select * from TEST");
        ResultSet result = st.getResultSet();
        while (!result.isLast()) {
            result.next();
            System.out.println(result.getString("NAME"));
        }
        conn.close();*/
    }


}