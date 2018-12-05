package ru.otus.hw11hibernate.orm.dbservice;

import ru.otus.hw11hibernate.DataSet;

import javax.management.ObjectName;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ExecutorOrm {

    private final Connection connection;
    public DBServiceOrmImpl dbServiceOrm = null;

    public ExecutorOrm(Connection connection){
        this.connection = connection;
    }

    public <T extends DataSet> long save(T obj) {
        long key = -1L;

        HashMap<String, Object> fieldsValue = new HashMap<>();
        Class classInfo = obj.getClass();

        for (Field declareField : obj.getClass().getDeclaredFields()) {
            try {
                declareField.setAccessible(true);
                Object declareFieldValue = declareField.get(obj);
                if (DataSet.class.isAssignableFrom(declareField.getType())) {
                    declareFieldValue = save((DataSet) declareField.get(obj));
                }

                fieldsValue.put(declareField.getName(), declareFieldValue);
                /*
                if (Collection.class.isAssignableFrom(declareField.getType())) {
                    for (Object arrayItem : (Collection) declareField.get(obj)) {
                        if (DataSet.class.isAssignableFrom(arrayItem.getClass()))
                        {
                            save((DataSet)arrayItem);
                        }
                    }
                }
                */
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }


        try {
            key = save(dbServiceOrm.classListConfig.get(obj.getClass()).getSqlInsert(), handler -> {
                int order = 1;
                for (Map.Entry<String, Object> item : fieldsValue.entrySet()) {
                    handler.setObject(order, item.getValue());
                    order++;
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return key;
    }

    public long save(String sql, ExecuteHandler executeHandler) throws SQLException {
        long result = -1L;
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            boolean autoCommit = preparedStatement.getConnection().getAutoCommit();
            preparedStatement.getConnection().setAutoCommit(false);
            executeHandler.accept(preparedStatement);
            System.out.println("upd : " + preparedStatement.executeUpdate());
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()){
                result = resultSet.getLong("id");
                System.out.println("new id :" + result);
            }
            preparedStatement.getConnection().setAutoCommit(autoCommit);
            preparedStatement.getConnection().commit();
        }
        return result;
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
        void accept(PreparedStatement statement) throws SQLException;
    }

    @FunctionalInterface
    public interface ResultHandler {
        void handle(ResultSet resultSet) throws SQLException, NoSuchFieldException, IllegalAccessException;
    }
}
