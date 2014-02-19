package com.compomics.pepshell;

import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.controllers.DataModes.CoLimsDataMode;
import com.compomics.pepshell.controllers.DataModes.FastaDataMode;
import com.compomics.pepshell.controllers.DataModes.MsLimsDataMode;
import com.compomics.pepshell.controllers.DataSources.AbstractDataSource;
import com.compomics.pepshell.controllers.DataSources.DataBaseDataSource;
import com.compomics.pepshell.controllers.DataSources.FileDataSource;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Davy
 */
public class DataModeController {

    private static Db iScheme;
    private static DataSource iDataSource;

    //TODO extract these two enums to the enum package
    public enum Db {

        MSLIMS(new MsLimsDataMode()), COLIMS(new CoLimsDataMode()), FASTA(new FastaDataMode());
        private final AbstractDataMode dataMode;

        Db(AbstractDataMode aDataMode) {
            this.dataMode = aDataMode;
        }

        public AbstractDataMode getDataMode() {
            return dataMode;
        }
    }

    public enum DataSource {

        FILE(new FileDataSource()), DATABASE(new DataBaseDataSource());
        AbstractDataSource dataSource;

        DataSource(AbstractDataSource aDataSource) {
            this.dataSource = aDataSource;
        }

        public AbstractDataSource getDataSource() {
            return dataSource;
        }
    }

    public static void checkDbScheme() throws SQLException {
        try (PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.CHECKIFCOLIMS); 
                ResultSet rs = stat.executeQuery()) {
            if (rs.next()) {
                SQLStatements.instantiateColimsStatements();
                iScheme = Db.COLIMS;
            } else {
                SQLStatements.instantiateMslimsStatements();
                iScheme = Db.MSLIMS;
                SQLStatements.instantiateLinkDbStatements();
            }
        }
    }

    public static Db getDb() {
        return iScheme;
    }

    public static Db getDb(boolean checkConnection) throws SQLException {
        if (checkConnection) {
            checkDbScheme();
        }
        return iScheme;
    }

    public static DataSource getDataSource() {
        return iDataSource;
    }

    public static void setDataSource(DataSource aDataSource) {
        DataModeController.iDataSource = aDataSource;
    }

    public static void setDb(Db aDb) {
        DataModeController.iScheme = aDb;
    }
}
