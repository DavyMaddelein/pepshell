package com.compomics.pepshell.controllers.DAO;

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

    public static Set<PdbInfo> getPDBFileAccessionsForProtein(Protein protein) throws MalformedURLException, IOException, ConversionException {
        Set<PdbInfo> infoSet = new HashSet<PdbInfo>();
        try {
            PreparedStatement stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getPdbFilesFromDb());
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                PdbInfo pdbInfo = new PdbInfo();
                pdbInfo.setPdbAccession(rs.getString("PDB"));
                infoSet.add(pdbInfo);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabasePDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return infoSet;

    }

}
