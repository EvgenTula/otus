package ru.otus.hw10orm;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBService {

    private static final String CREATE_TABLE_USER = "create table if not exists user (id bigint auto_increment, name varchar(255), age int, primary key (id))";
    private static final String CLEAR_TABLE = "delete from user";

    private final Connection connection;
    private final Executor executor;

    public DBService() {
        this.connection = ConnectionHelper.getConnection();
        this.executor = new Executor(this.connection);
        try {
            this.prepareTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSet> void save(T user) {
        try {
            this.executor.save(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        return this.executor.load(id,clazz);
    }
    private void prepareTables() throws SQLException {
        try(Statement st = connection.createStatement())
        {
            st.execute(CREATE_TABLE_USER);
            st.execute(CLEAR_TABLE);
        }
    }
}
