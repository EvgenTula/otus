package ru.otus.hw11hibernate.orm.dbservice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor {

    private final Connection connection;


    public Executor(Connection connection){
        this.connection = connection;
    }
      public void save(String sql, ExecuteHandler executeHandler) throws SQLException {
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql))
        {
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
