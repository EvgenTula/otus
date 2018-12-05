package ru.otus.hw11hibernate.orm.dbservice;

import ru.otus.hw11hibernate.DataSet;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor {

    private final Connection connection;

    public Executor(Connection connection){
        this.connection = connection;
    }
/*
    private static String parseObject(Object obj) {
        if (isSimpleObject(obj.getClass()))
        {
            return parseSimpleObject(obj);
        }
        else
        {
            return parseCompositeObject(obj);
        }
    }

    private static boolean isSimpleObject(Class classInfo) {
        return  (classInfo.getCanonicalName().equals("java.lang.String")
                ||
                classInfo.getCanonicalName().equals("java.lang.Character")
                ||
                setClasses.contains(classInfo)
                ||
                classInfo.isArray()
                ||
                Collection.class.isAssignableFrom(classInfo)
        );
    }
*/
    public <T extends DataSet> void save(T obj) {
        for (Field declareField : obj.getClass().getDeclaredFields()){
            try {
                declareField.setAccessible(true);
                if (DataSet.class.isAssignableFrom(declareField.getType())) {
                //if (declareField.getType().getSuperclass().isAssignableFrom(DataSet.class)) {
                    save((DataSet)declareField.get(obj));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(String sql, ExecuteHandler executeHandler) throws SQLException {
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql)) {
            executeHandler.accept(preparedStatement);
            preparedStatement.execute();
        }
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
