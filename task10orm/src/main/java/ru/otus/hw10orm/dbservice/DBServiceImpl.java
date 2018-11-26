package ru.otus.hw10orm.dbservice;

import ru.otus.hw10orm.dataset.DataSet;
import ru.otus.hw10orm.dataset.UserDataSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DBServiceImpl implements DBService {

    private static final String TABLE_NAME = "user";
    private static final String CREATE_TABLE_USER = "create table " + TABLE_NAME + " (id bigint auto_increment, name varchar(255), age int, primary key (id))";
    private static final String DROP_TABLE = "drop table if exists " + TABLE_NAME;
    private final Connection connection;
    private final Executor executor;
    private HashMap<Class,List<String>> classesFields;


    public DBServiceImpl() {
        this.classesFields = new HashMap<>();
        this.addClass(UserDataSet.class);
        this.connection = ConnectionHelper.getConnection();
        this.executor = new Executor(this.connection);
        try {
            this.prepareTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> void save(T user) {
        try {
            StringJoiner fieldList = new StringJoiner(",");
            StringJoiner valueField = new StringJoiner(",");
            for (Map.Entry<String, Object> item : getFieldsValue(user).entrySet()) {
                fieldList.add(item.getKey());
                valueField.add("?");
            }

            StringBuilder sql = new StringBuilder("insert into " + TABLE_NAME + "( " + fieldList.toString() + " ) values ( " + valueField + ")");

            this.executor.save(sql.toString(),handler -> {
                int order = 1;
                for (Map.Entry<String, Object> item : getFieldsValue(user).entrySet()) {
                    handler.setObject(order, item.getValue());
                    order++;
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {

        try {
            T result = clazz.getDeclaredConstructor().newInstance();;
            this.executor.load(id, getSelectQuery(clazz), handle -> {
                handle.next();
                for (String fieldName : classesFields.get(clazz)){
                    clazz.getField(fieldName).set(result,handle.getObject(fieldName));
                }
            });
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getSelectQuery(Class classInfo) {
        return new String("select " + getFields(classInfo) + " from " + TABLE_NAME + " where id = ?");
    }

    private String getFields(Class clazz) {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String fieldName : classesFields.get(clazz)) {
            stringJoiner.add(fieldName);
        }
        return stringJoiner.toString();
    }

    private <T extends DataSet> HashMap<String, Object> getFieldsValue(T obj) {
        HashMap<String, Object> result = new HashMap<>();
        Class classInfo = obj.getClass();
        if (classesFields.get(classInfo) != null)
        {
            for (String fieldName : classesFields.get(classInfo)){
                try {
                    result.put(fieldName,classInfo.getField(fieldName).get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private void addClass(Class classInfo) {
        if (classesFields.get(classInfo) == null) {
            List<String> fields = new ArrayList<>();
            for (Field field: classInfo.getDeclaredFields()) {
                if (Modifier.isPublic(field.getModifiers())) {
                    fields.add(field.getName());
                }
            }
            if (fields.size() > 0) {
                classesFields.put(classInfo, fields);
            }
        }
    }

    private void prepareTables() throws SQLException {
        try(Statement st = connection.createStatement())
        {
            st.execute(DROP_TABLE);
            st.execute(CREATE_TABLE_USER);
        }
    }
}
