package com.compomics.pepshell.controllers.DataSources.StructureDataSources;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.PDBDAO;
import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Davy
 * @param <T>
 */
public class LinkDb implements StructureDataSource {
//TODO subtype this to uniprot protein since we need to be sure we have uniprot accessions

    public String getPDBDataForPDBName(String pdbName) {
        String pdbData = "no data found";
        PreparedStatement stat;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getPdbDataFromDb());
            stat.setString(1, pdbName);
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    pdbData = "technique: " + rs.getString("technique") + " \n" + "resolution: " + rs.getString("resolution");
                    try {
                        PDBDAO.getPdbInfoForPdbAccession(pdbName);
                        //TODO if online fetch title from pbd
                    } catch (IOException ex) {
                        Logger.getLogger(LinkDb.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (XMLStreamException ex) {
                        Logger.getLogger(LinkDb.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (SQLException sqle) {
            }
        } catch (SQLException sqle) {
        }

        return pdbData;

    }

    public List<Domain> getDomainData(Protein aProtein) throws DataRetrievalException {
        List<Domain> foundDomains = new ArrayList<Domain>();
        try {
            foundDomains = ExternalDomainFinder.getDomainsForUniprotAccessionFromSingleSource(AccessionConverter.ToUniprot(aProtein.getProteinAccession()), ExternalDomainFinder.DomainWebSites.PFAM);
        } catch (IOException ex) {
            throw new DataRetrievalException("");
        } catch (ConversionException ex) {
            throw new DataRetrievalException("");
        } catch (XMLStreamException ex) {
            throw new DataRetrievalException("");
        }
        return foundDomains;
    }

    public List<InteractionPartner> getInteractionPartnersForRange(Protein aProtein, int start, int stop) {
        List<InteractionPartner> interactionPartnerList = new ArrayList<InteractionPartner>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getInteractionPartnersForRange());
            stat.setString(1, aProtein.getProteinAccession());
            stat.setInt(2, start);
            stat.setInt(3, stop);
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    interactionPartnerList.add(new InteractionPartner(rs.getInt("residuepartner1"), rs.getInt("residuepartner2"), rs.getString("interaction_type")));
                }
            } catch (SQLException sqle) {
            }
        } catch (SQLException sqle) {
        }
        return interactionPartnerList;
    }

    public List<InteractionPartner> getInteractionPartners(Protein aProtein) {
        List<InteractionPartner> interactionPartnerList = new ArrayList<InteractionPartner>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getInteractionPartnersForResidue());
            stat.setString(1, aProtein.getProteinAccession());
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    interactionPartnerList.add(new InteractionPartner(rs.getInt("residuepartner1"), rs.getInt("residuepartner2"), rs.getString("interaction_type")));
                }
            } catch (SQLException sqle) {
            }
        } catch (SQLException sqle) {
        }
        return interactionPartnerList;
    }

    public boolean isAbleToGetFreeEnergy() {
        return true;
    }

    public boolean isAbleToGetSolventAccessibility() {
        return true;
    }

    public double getFreeEnergyForResidue(Protein protein, int location) {
        double freeEnergy = -1;
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.GetFreeEnergyForResidue());
            stat.setString(1, protein.getVisibleAccession());
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    freeEnergy = Double.parseDouble(rs.getString("e_total"));
                }
            } catch (SQLException sqle) {
            }
        } catch (SQLException sqle) {
        }

        return freeEnergy;
    }

    public double getRelativeSolventAccessibilityForResidue(Protein protein, int location) {
        double solventAccessability = -1;
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getRelativeSolventAccessibilityForResidue());
            stat.setString(1, protein.getVisibleAccession());
            stat.setInt(2, location);
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    solventAccessability = Double.parseDouble(rs.getString("SASrel"));
                }
            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
            }
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
        }

        return solventAccessability;
    }

    public void getSecondaryStructureForResidue(Protein protein, int location) {
        //PDBDAO.(protein)
    }

    public List<InteractionPartner> getInteractionPartnersForPDBName(String pdbName) {
        List<InteractionPartner> interactionPartnerList = new ArrayList<InteractionPartner>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getInteractionPartnersForPDB());
            stat.setString(1, pdbName);
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    interactionPartnerList.add(new InteractionPartner(rs.getInt("residuepartner1"), rs.getInt("residuepartner2"), rs.getString("interaction_type")));
                }
            } catch (SQLException sqle) {
            }
        } catch (SQLException sqle) {
        }
        return interactionPartnerList;
    }
}
