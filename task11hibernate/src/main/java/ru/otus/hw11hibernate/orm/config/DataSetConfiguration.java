package ru.otus.hw11hibernate.orm.config;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringJoiner;

public class DataSetConfiguration {

    public Class classInfo;
    private String tableName;
    private String sqlSelect;
    private String sqlCreateTable;
    private String sqlDropTable;
    private String sqlInsert;
    private List<Field> fieldList;

    public DataSetConfiguration(Class classInfo, String tableName, String sqlCreateTableColumns) {
        this.classInfo = classInfo;
        this.tableName = tableName;
        this.fieldList = new ArrayList<>();

        for (Field field: classInfo.getDeclaredFields()) {
            fieldList.add(field);
        }

        fillSqlDropTable();
        fillSqlCreateTable(sqlCreateTableColumns);
        fillSqlSelect();
        fillSqlInsert();
    }

    public String getSqlSelect() {
        return this.sqlSelect;
    }

    public String getSqlDropTable() {
        return this.sqlDropTable;
    }

    public String getSqlCreateTable() {
        return this.sqlCreateTable;
    }

    public String getSqlInsert() {
        return this.sqlInsert;
    }

    public List<Field> getFieldList() {
        return this.fieldList;
    }

    private void fillSqlSelect() {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Field field : fieldList) {
            if (!Collection.class.isAssignableFrom(field.getType())) {
                stringJoiner.add(field.getName());
            }
        }
        this.sqlSelect = new String("select " + stringJoiner.toString() + " from " + this.tableName + " where id = ?");
    }

    private void  fillSqlDropTable() {
        this.sqlDropTable = "drop table if exists " + this.tableName;
    }

    private void fillSqlCreateTable(String sqlCreateTableColumns) {
        this.sqlCreateTable = "create table " + this.tableName + " " + sqlCreateTableColumns;
    }

    private void fillSqlInsert() {
        StringJoiner nameFieldJoiner = new StringJoiner(",");
        StringJoiner valueFieldJoiner = new StringJoiner(",");
        for (Field field : this.fieldList) {
            if (!Collection.class.isAssignableFrom(field.getType())) {
                nameFieldJoiner.add(field.getName());
                valueFieldJoiner.add("?");
            }
        }
        this.sqlInsert = "insert into " + this.tableName + "( " + nameFieldJoiner.toString() + " ) values (" + valueFieldJoiner.toString() + ")";
    }
}