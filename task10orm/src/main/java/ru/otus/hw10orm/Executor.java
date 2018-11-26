package ru.otus.hw10orm;

import ru.otus.hw10orm.dataset.DataSet;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Executor {

    private final Connection connection;


    public Executor(Connection connection){
        this.connection = connection;
    }
      public <T extends DataSet> void save(T user, String sql) throws SQLException {

        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql))
        {
            preparedStatement.setLong(1,user.id);
            if (preparedStatement.execute()) {

            }
            /*
            preparedStatement.execute("");
            if (preparedStatement.executestatement.getResultSet().wasNull()) {

            }
            */
            //st.execute(CREATE_TABLE_USER);
        }
    }

    public ResultSet load(long id, String sql) throws SQLException {
        try(PreparedStatement preparedStatement = this.connection.prepareStatement(sql))
        {
            preparedStatement.setLong(1,id);
            preparedStatement.execute();
            return preparedStatement.getResultSet();
        }
    }

}
