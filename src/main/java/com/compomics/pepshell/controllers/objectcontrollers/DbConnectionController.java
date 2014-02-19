package com.compomics.pepshell.controllers.objectcontrollers;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Davy
 */
public class DbConnectionController {

//change to connectionpool
    private static volatile Connection connection;
    private static volatile Connection linkdbConnection;

    public static Connection createConnection(String username, String password, String url, String database) throws SQLException {
        if (connection == null) {
            connection = connect(username, password, url, database);
        }
        return DbConnectionController.getConnection();
    }

    public static Connection createLinkDbConnection(String username, String password, String url, String database) throws SQLException {
        if (linkdbConnection == null) {
            linkdbConnection = connect(username, password, url, database);
        }
        return DbConnectionController.getLinkDBConnection();
    }

    private static Connection connect(String username, String password, String url, String database) throws SQLException {
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
        return dbSource.getConnection();
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null) {
            throw new SQLException("connection has not been made");
        }
        return connection;
    }

    public static Connection getLinkDBConnection() throws SQLException {
        if (linkdbConnection == null) {
            throw new SQLException("connection to linkdb not made");
        }
        return linkdbConnection;
    }

    public static boolean isDbConnected() {
        return connection != null;
    }

    public static boolean isLinkDbConnected() {
        return linkdbConnection != null;
    }
}
