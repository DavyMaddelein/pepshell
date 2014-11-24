package com.compomics.pepshell;

import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.enums.DataModeEnum;
import com.compomics.pepshell.model.enums.DataSourceEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Davy Maddelein
 */
public class DataModeController {

    private static DataModeEnum iScheme;
    private static DataSourceEnum iDataSource;
    private static final DataModeController instance = new DataModeController();

    private DataModeController(){}
    
    public static DataModeController getInstance(){
        return instance;
    }
    
    //needs to be generic
    public void checkDbScheme() throws SQLException {
        try (PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(SQLStatements.CHECKIFCOLIMS); 
                ResultSet rs = stat.executeQuery()) {
            if (rs.next()) {
                SQLStatements.instantiateColimsStatements();
                iScheme = DataModeEnum.COLIMS;
            } else {
                iScheme = DataModeEnum.MSLIMS;
                SQLStatements.instantiateLinkDbStatements();
            }
        }
    }

    public DataModeEnum getDb() {
        return iScheme;
    }

    public DataModeEnum getDb(boolean checkConnection) throws SQLException {
        if (checkConnection) {
            checkDbScheme();
        }
        return iScheme;
    }

    public DataSourceEnum getDataSource() {
        return iDataSource;
    }

    public void setDataSource(DataSourceEnum aDataSource) {
        DataModeController.iDataSource = aDataSource;
    }
}
