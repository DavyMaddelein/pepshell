package com.compomics.pepshell;

import com.compomics.pepshell.controllers.dataimport.databasehandlers.ImportFromDatabaseHandler;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.enums.DataModeEnum;
import com.compomics.pepshell.model.enums.DataSourceEnum;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Davy Maddelein
 */
public class DataModeController {

    private static DataModeEnum iScheme;
    private boolean found = false;
    private static DataSourceEnum iDataSource;
    ImportFromDatabaseHandler<Connection> databaseHandler = new ImportFromDatabaseHandler<>();
    private static final DataModeController instance = new DataModeController();

    private DataModeController() {
    }

    public static DataModeController getInstance() {
        return instance;
    }

    public void selectDataHandler() throws SQLException {
        if (DbConnectionController.getExperimentDbConnection() != null) {
            databaseHandler.canHandle(DbConnectionController.getExperimentDbConnection());
            SQLStatements.instantiateLinkDbStatements();
        } else {
            iScheme = DataModeEnum.FILE;
        }
    }

    public DataModeEnum getDb() {
        return iScheme;
    }

    public DataModeEnum getDb(boolean checkConnection) throws SQLException {
        if (checkConnection) {
            selectDataHandler();
        }
        return iScheme;
    }

    public DataSourceEnum getDataSource() {
        return iDataSource;
    }

    public void setDataSource(DataSourceEnum aDataSource) {
        DataModeController.iDataSource = aDataSource;
    }

    public void setDataMode(DataModeEnum aDataMode) {
        iScheme = aDataMode;
    }
}
