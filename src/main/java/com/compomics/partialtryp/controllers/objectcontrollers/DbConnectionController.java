/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.controllers.objectcontrollers;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Davy
 */
public class DbConnectionController {

    private static Connection connection;

    public static Connection createConnection(String username, String password, String url, String database) throws SQLException{
        if (connection == null){
            new DbConnectionController(username,password,url,database);
            return DbConnectionController.getConnection();
        } else {
            return getConnection();
        }
    }

    private DbConnectionController(String username, String password, String url, String database) throws SQLException {
        MysqlDataSource dbSource = new MysqlDataSource();
        dbSource.setServerName(url);
            dbSource.setDatabaseName(database);
            dbSource.setUser(username);
            dbSource.setPassword(password);
            connection = dbSource.getConnection();
    }

    public static Connection getConnection() {
        return connection;
    }
}
