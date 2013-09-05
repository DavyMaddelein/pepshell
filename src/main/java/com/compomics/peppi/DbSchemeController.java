package com.compomics.peppi;

import com.compomics.peppi.controllers.DataModes.AbstractDataMode;
import com.compomics.peppi.controllers.DataModes.CoLimsDataMode;
import com.compomics.peppi.controllers.DataModes.ElienDbDataMode;
import com.compomics.peppi.controllers.DataModes.MsLimsDataMode;
import com.compomics.peppi.controllers.objectcontrollers.DbConnectionController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Davy
 */
public class DbSchemeController {

    private static DbScheme iScheme;

    public enum DbScheme {

        MSLIMS(new MsLimsDataMode()), COLIMS(new CoLimsDataMode()), ELIENDB(new ElienDbDataMode());
        private final AbstractDataMode dataMode;

        DbScheme(AbstractDataMode aDataMode) {
            this.dataMode = aDataMode;
        }

        public AbstractDataMode getDataMode() {
            return dataMode;
        }
    }

    public static void checkDbScheme() throws SQLException, NullPointerException {
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.CHECKIFCOLIMS);
            ResultSet rs = stat.executeQuery();
            try {
                if (rs.next()) {
                    SQLStatements.instantiateColimsStatements();
                    iScheme = DbScheme.COLIMS;
                } else {
                    SQLStatements.instantiateMslimsStatements();
                    iScheme = DbScheme.MSLIMS;
                }
            } finally {
                rs.close();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
        }
    }

    public static DbScheme getDbScheme() throws SQLException {
        if (iScheme == null) {
            checkDbScheme();
        }
        return iScheme;
    }
}
