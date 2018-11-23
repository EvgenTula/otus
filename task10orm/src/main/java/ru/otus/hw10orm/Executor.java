package ru.otus.hw10orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Executor {

    private final Connection connection;

    public Executor(Connection connection){
        this.connection = connection;
    }

    public <T extends DataSet> void save(T user) throws SQLException {
        try(PreparedStatement statement = connection.prepareStatement(SELECT_DATAROW))
        {
            statement.setLong(1,user.id);
            statement.execute(SELECT_DATAROW);
            if (statement.getResultSet().wasNull()) {

            }
            //st.execute(CREATE_TABLE_USER);
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        T result = null;
        try(Statement st = connection.createStatement())
        {
            st.execute(CREATE_TABLE_USER);
        }
        return result;
    }

}
