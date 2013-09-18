package com.compomics.peppi.controllers.objectcontrollers;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Davy
 */
public class DbConnectionController {

    private static Connection connection;

    public static Connection createConnection(String username, String password, String url, String database) throws SQLException {
        if (connection == null) {
            new DbConnectionController(username, password, url, database);
        }
        return DbConnectionController.getConnection();
    }

    private DbConnectionController(String username, String password, String url, String database) throws SQLException {
        MysqlDataSource dbSource = new MysqlDataSource();
        if (url.contains(":")) {
            String[] urlAndPort = url.split(":");
            dbSource.setServerName(urlAndPort[0]);
            dbSource.setPortNumber(Integer.valueOf(urlAndPort[1]));
        } else {
            dbSource.setServerName(url);
        }
        dbSource.setDatabaseName(database);
        dbSource.setUser(username);
        dbSource.setPassword(password);
        connection = dbSource.getConnection();
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            throw new SQLException("No connection has been made yet");
        }
        return connection;
    }
}
