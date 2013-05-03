/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.peppi;

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

        MSLIMS, COLIMS,ELIENDB
    }

    public static void checkDbScheme() throws SQLException, NullPointerException {
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.CHECKIFCOLIMS);
        ResultSet rs = stat.executeQuery();
        if (rs.next()) {
            SQLStatements.instantiateColimsStatements();
            iScheme = DbScheme.COLIMS;
        } else {
            SQLStatements.instantiateMslimsStatements();
            iScheme = DbScheme.MSLIMS;
        }
        rs.close();
        stat.close();
    }

    public static DbScheme getDbScheme() throws SQLException {
        if (iScheme == null) {
            checkDbScheme();
        }
        return iScheme;
    }
}
