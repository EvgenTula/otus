package ru.otus.hw10orm.dbservice;

import ru.otus.hw10orm.dataset.DataSet;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DBServiceImpl implements DBService {

    private final Connection connection;
    private final Executor executor;
    private HashMap<Class,DataSetConfiguration> classListConfig;

    public DBServiceImpl() {
        this.classListConfig = new HashMap<>();
        this.connection = ConnectionHelper.getConnection();
        this.executor = new Executor(this.connection);
    }

    @Override
    public <T extends DataSet> void save(T user) {
        try {
            this.executor.save(classListConfig.get(user.getClass()).getSqlInsert(),handler -> {
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
            this.executor.load(id, classListConfig.get(clazz).getSqlSelect(), handle -> {
                handle.next();
                for (String fieldName : classListConfig.get(clazz).getFieldList()){
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

    private <T extends DataSet> HashMap<String, Object> getFieldsValue(T obj) {
        HashMap<String, Object> result = new HashMap<>();
        Class classInfo = obj.getClass();
        if (classListConfig.get(classInfo) != null)
        {
            for (String fieldName : classListConfig.get(classInfo).fieldList){
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

    @Override
    public void addClass(Class classInfo, String tableName) {
        if (!classListConfig.containsKey(classInfo)) {
            classListConfig.put(classInfo, new DataSetConfiguration(classInfo, tableName));
            try {
                this.prepareTables(classInfo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void prepareTables(Class clazz) throws SQLException {
        try(Statement st = connection.createStatement()) {
            st.execute(classListConfig.get(clazz).getSqlDropTable());
            st.execute(classListConfig.get(clazz).getSqlCreateTable());
        }
    }
}
