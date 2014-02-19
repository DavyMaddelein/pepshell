package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Davy
 */
public class DatabasePDBDAO extends PDBDAO {

    private static volatile DatabasePDBDAO instance;

    public static DatabasePDBDAO getInstance() {
        if (instance == null) {
            instance = new DatabasePDBDAO();
        }
        return instance;
    }

    public static Set<PdbInfo> getPDBFileAccessionsForProtein(Protein protein) throws MalformedURLException, IOException, ConversionException {
        Set<PdbInfo> infoSet = new HashSet<>();
        try {
            PreparedStatement stat;
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getPdbFilesFromDb());
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                PdbInfo pdbInfo = new PdbInfo();
                pdbInfo.setPdbAccession(rs.getString("PDB"));
                infoSet.add(pdbInfo);
            }
        } catch (SQLException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        return infoSet;
    }

    @Override
    public Set<PdbInfo> getPDBInfoForProtein(Protein protein) throws MalformedURLException, IOException, ConversionException {
        Set<PdbInfo> infoSet = new HashSet<>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getPdbInfoForProtein());
            //stat.setString(1,protein.getVisibleAccession);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                PdbInfo info = new PdbInfo();
                info.setMethod(rs.getString("PDB"));
                info.setName(rs.getString("title"));
                info.setResolution(Double.parseDouble(rs.getString("resolution")));
                infoSet.add(info);
            }
        } catch (SQLException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        return infoSet;
    }

}
