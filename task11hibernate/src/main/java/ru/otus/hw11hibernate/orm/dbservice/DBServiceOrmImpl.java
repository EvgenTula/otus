package ru.otus.hw11hibernate.orm.dbservice;

import ru.otus.hw11hibernate.*;
import ru.otus.hw11hibernate.orm.dataset.AddressDataSetOrm;
import ru.otus.hw11hibernate.orm.dataset.PhoneDataSetOrm;
import ru.otus.hw11hibernate.orm.dataset.UserDataSetOrm;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class DBServiceOrmImpl implements DBService {

    private final Connection connection;
    private final Executor executor;
    private HashMap<Class,DataSetConfiguration> classListConfig;

    public DBServiceOrmImpl(List<DataSetConfiguration> configurationList) {
        this.connection = ConnectionHelper.getConnection();
        this.executor = new Executor(this.connection);
        this.classListConfig = new HashMap<>();
        for (DataSetConfiguration config : configurationList) {
            this.classListConfig.put(config.classInfo, config);
            try {
                prepareTables(config.classInfo);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
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
            T result = clazz.getDeclaredConstructor().newInstance();
            this.executor.load(id, classListConfig.get(clazz).getSqlSelect(), handle -> {
                handle.next();
                for (Field declaredField : classListConfig.get(clazz).getFieldList()){
                    declaredField.setAccessible(true);
                    declaredField.set(result,handle.getObject(declaredField.getName()));
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
            for (Field declareField : classListConfig.get(classInfo).getFieldList()){
                try {
                    declareField.setAccessible(true);
                    result.put(declareField.getName(),declareField.get(obj));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private void prepareTables(Class clazz) throws SQLException {
        try(Statement st = connection.createStatement()) {
            st.execute(classListConfig.get(clazz).getSqlDropTable());
            st.execute(classListConfig.get(clazz).getSqlCreateTable());
        }
    }
}