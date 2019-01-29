package ru.otus.hw10orm.dbservice;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class DataSetConfiguration {

    public Class classInfo;
    private String tableName;
    private String sqlSelect;
    private String sqlCreateTable;
    private String sqlDropTable;
    private String sqlInsert;
    List<String> fieldList;

    public DataSetConfiguration(Class classInfo, String tableName) {
        this.classInfo = classInfo;
        this.tableName = tableName;
        this.fieldList = new ArrayList<>();

        for (Field field: classInfo.getDeclaredFields()) {
            if (Modifier.isPublic(field.getModifiers())) {
                fieldList.add(field.getName());
            }
        }

        fillSqlDropTable();
        fillSqlCreateTable();
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

    public List<String> getFieldList() {
        return this.fieldList;
    }

    private void fillSqlSelect() {
        StringJoiner stringJoiner = new StringJoiner(",");
        for (String fieldName : fieldList) {
            stringJoiner.add(fieldName);
        }
        this.sqlSelect = new String("select " + stringJoiner.toString() + " from " + this.tableName + " where id = ?");
    }

    private void  fillSqlDropTable() {
        this.sqlDropTable = "drop table if exists " + this.tableName;
    }

    private void fillSqlCreateTable() {
        this.sqlCreateTable = "create table " + this.tableName + " (id bigint auto_increment, name varchar(255), age int, primary key (id))";
    }

    private void fillSqlInsert() {
        StringJoiner nameFieldJoiner = new StringJoiner(",");
        StringJoiner valueFieldJoiner = new StringJoiner(",");
        for (String field : this.fieldList) {
            nameFieldJoiner.add(field);
            valueFieldJoiner.add("?");
        }
        this.sqlInsert = "insert into " + this.tableName + "( " + nameFieldJoiner.toString() + " ) values (" + valueFieldJoiner.toString() + ")";
    }
}