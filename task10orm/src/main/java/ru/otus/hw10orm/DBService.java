package ru.otus.hw10orm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class DBService {

    private final Connection connection;

    public DBService(List<DataSet> classList) {
        this.connection = ConnectionHelper.getConnection();
        for (DataSet item : classList) {
            if (!checkTable(item.getTableName())) {
                System.out.println();
            }
        }
    }

    private static final String CREATE_TABLE_USER = "create table if not exists user (id bigint auto_increment, name varchar(255), age int, primary key (id))";
    private static final String CLEAR_TABLE = "delete from user";
    private static final String CHECK_TABLE = "SELECT count(*) cnt FROM INFORMATION_SCHEMA.TABLES where TABLE_NAME = ?";


    private static final String INSERT_USER = "insert into user (name) values ('%s')";
    private static final String DELETE_USER = "drop table user";

    public void prepareTables() throws SQLException {
        try(Statement st = connection.createStatement())
        {
            st.execute(CREATE_TABLE_USER);
            st.execute(CLEAR_TABLE);
        }
    }

    public <T extends DataSet> void save(T user) throws SQLException {
        try(Statement st = connection.createStatement())
        {
            //st.execute(CREATE_TABLE_USER);
        }
    }

    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        /*try(Statement st = connection.createStatement())
        {
            st.execute(CREATE_TABLE_USER);
        }*/
        return null;
    }

    private boolean checkTable(String tableName) {
        try(PreparedStatement st = connection.prepareStatement(CHECK_TABLE))
        {
            st.setString(1,tableName);
            st.execute();
            return Boolean.parseBoolean(st.getResultSet().getString("cnt"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
