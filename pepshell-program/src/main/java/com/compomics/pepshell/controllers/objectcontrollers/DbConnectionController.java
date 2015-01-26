/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.controllers.objectcontrollers;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Davy Maddelein
 */
public class DbConnectionController {

//change to connectionpool
    private static volatile Connection experimentDbConnection;
    private static volatile Connection structDbConnection;

    private DbConnectionController() {
    }

    public static Connection createExperimentDbConnection(String username, String password, String url, String database) throws SQLException {
        if (experimentDbConnection == null) {
            experimentDbConnection = connect(username, password, url, database);
        }
        return DbConnectionController.getExperimentDbConnection();
    }

    public static Connection createStructDbConnection(String username, String password, String url, String database) throws SQLException {
        if (structDbConnection == null) {
            structDbConnection = connect(username, password, url, database);
        }
        return DbConnectionController.getStructDBConnection();
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

    public static Connection getExperimentDbConnection() throws SQLException {
        if (experimentDbConnection == null) {
            throw new SQLException("connection to the experiment database not made");
        }
        return experimentDbConnection;
    }

    public static Connection getStructDBConnection() throws SQLException {
        if (structDbConnection == null) {
            throw new SQLException("connection to the structural database not made");
        }
        return structDbConnection;
    }

    private static boolean isExperimentDbConnected() {
        return experimentDbConnection != null;
    }

    public static boolean isStructDbConnected() {
        return structDbConnection != null;
    }

    @Override
    public String toString() {
        return "manages connection to the experimental and structural databases \n"
                + "connected to experimental db :" + String.valueOf(isExperimentDbConnected()) + "\n"
                + "connected to strcutural db :" + String.valueOf(isStructDbConnected());
    }
}
