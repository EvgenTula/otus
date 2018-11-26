package ru.otus.hw10orm;

import ru.otus.hw10orm.dataset.DataSet;
import ru.otus.hw10orm.dataset.UserDataSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DBServiceImpl implements DBService {

    private static final String TABLE_NAME = "user";
    private static final String CREATE_TABLE_USER = "create table if not exists " + TABLE_NAME + " (id bigint auto_increment, name varchar(255), age int, primary key (id))";
    private static final String CLEAR_TABLE = "delete from " + TABLE_NAME;

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
            this.executor.save(user,"");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) {
        T result = null;
        try {
            ResultSet resultSet = this.executor.load(id, getSelectQuery(clazz));
            result = clazz.getDeclaredConstructor().newInstance();
            resultSet.next();
            for (String fieldName : classesFields.get(clazz)){
              clazz.getField(fieldName).set(result,resultSet.getObject(fieldName));
            }
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
        return result;
    }

    private String getSelectQuery(Class classInfo) {
        StringBuilder stringBuilder = new StringBuilder();
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String fieldName : classesFields.get(classInfo)) {
            stringJoiner.add(fieldName);
        }
        return stringBuilder.append("select " + stringJoiner.toString() + " from " + TABLE_NAME + " where id = ?").toString();
    }

    private List<String> getFields(Class clazz) {
        List<String> result = new ArrayList<>();
        for (String fieldName : classesFields.get(clazz)) {
            result.add(fieldName);
        }
        return result;
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
            st.execute(CREATE_TABLE_USER);
            st.execute(CLEAR_TABLE);
        }
    }
}
