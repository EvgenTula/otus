package ru.otus.hw11hibernate.orm.dbservice;

import ru.otus.hw11hibernate.DataSet;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.*;

public class ExecutorOrm {

    private final Connection connection;
    public DBServiceOrmImpl dbServiceOrm = null;

    public ExecutorOrm(Connection connection){
        this.connection = connection;
    }

    public <T extends DataSet> long save(T obj) {
        long key = -1L;

        List<Object> fieldsValue = new ArrayList<>();
        HashMap<Object, HashMap<String,Object>> afterInsertObject = new HashMap<>();

        for (Field field : dbServiceOrm.classListConfig.get(obj.getClass()).getFieldList()) {
            try {
                field.setAccessible(true);
                Object declareFieldValue = field.get(obj);

                if (Collection.class.isAssignableFrom(field.getType())) {
                    HashMap<String, Object> arrayItemFieldValue = new HashMap<>();
                    Class classInfo = null;
                    for (Object arrayItem : (Collection) field.get(obj)) {
                        if (DataSet.class.isAssignableFrom(arrayItem.getClass()))
                        {
                            classInfo = arrayItem.getClass();
                            arrayItemFieldValue = dbServiceOrm.getFieldsValue((DataSet)arrayItem);
                        }
                    }
                    if (arrayItemFieldValue.size() > 0) {
                        afterInsertObject.put(classInfo, arrayItemFieldValue);
                    }
                } else {
                    if (DataSet.class.isAssignableFrom(field.getType())) {
                        declareFieldValue = save((DataSet) field.get(obj));
                    }
                    fieldsValue.add(declareFieldValue);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        try {
            key = save(dbServiceOrm.classListConfig.get(obj.getClass()).getSqlInsert(), handler -> {
                int order = 1;
                for (Object val : fieldsValue) {
                    handler.setObject(order, val);
                    order++;
                }
            });

            for (Map.Entry<Object, HashMap<String, Object>> item : afterInsertObject.entrySet()) {
                long finalKey = key;
                save(dbServiceOrm.classListConfig.get(item.getKey()).getSqlInsert(), handler -> {
                    int order = 1;
                    for (Map.Entry<String, Object> fieldValue : item.getValue().entrySet()) {
                        if (fieldValue.getValue().getClass().isAssignableFrom(obj.getClass())) {
                            handler.setObject(order, finalKey);
                        }
                        else {
                            handler.setObject(order, fieldValue.getValue());
                        }
                        order++;
                    }
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    private long save(String sql, ExecuteHandler executeHandler) throws SQLException {
        long result = -1L;
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            boolean autoCommit = preparedStatement.getConnection().getAutoCommit();
            preparedStatement.getConnection().setAutoCommit(false);
            executeHandler.accept(preparedStatement);
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                result = resultSet.getLong("id");
            }
            preparedStatement.getConnection().setAutoCommit(autoCommit);
            preparedStatement.getConnection().commit();
        }
        return result;
    }

    public <T extends DataSet> T loadUser(long id, Class<T> clazz) {
        try {
            T result = clazz.getDeclaredConstructor().newInstance();

            load(id, dbServiceOrm.classListConfig.get(clazz).getSqlSelect(), resultSet -> {
            resultSet.next();
            for (Field field : dbServiceOrm.classListConfig.get(clazz).getFieldList()){
                field.setAccessible(true);
                if (Collection.class.isAssignableFrom(field.getType())) {
                    /*
                    HashMap<String, Object> arrayItemFieldValue = new HashMap<>();
                    Class classInfo = null;
                    for (Object arrayItem : (Collection) field.get(obj)) {
                        if (DataSet.class.isAssignableFrom(arrayItem.getClass()))
                        {
                            classInfo = arrayItem.getClass();
                            arrayItemFieldValue = dbServiceOrm.getFieldsValue((DataSet)arrayItem);
                        }
                    }
                    if (arrayItemFieldValue.size() > 0) {
                        afterInsertObject.put(classInfo, arrayItemFieldValue);
                    }*/
                } else {
                    Object declareFieldValue = resultSet.getObject(field.getName());
                    if (DataSet.class.isAssignableFrom(field.getType())) {
                        declareFieldValue = (Object) loadUser((long)declareFieldValue, (Class<T>) field.getType());
                    }
                    field.set(result,declareFieldValue);
                }
            }});
            return result;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void load(long id, String sql, ResultHandler resultHandler) throws SQLException, NoSuchFieldException, IllegalAccessException {
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql))
        {
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
            resultHandler.handle(preparedStatement.getResultSet());
        }
    }

    @FunctionalInterface
    public interface ExecuteHandler {
        void
        accept(PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    public interface ResultHandler {
        void handle(ResultSet resultSet) throws SQLException, NoSuchFieldException, IllegalAccessException;
    }
}
