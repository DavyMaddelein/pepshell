package com.compomics.pepshell.controllers.DataSources.StructureDataSources;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.PDBDAO;
import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Ordering;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.stream.XMLStreamException;

/**
 *
 * @author Davy
 * @param <T>
 */
public class LinkDb implements StructureDataSource {
//TODO subtype this to uniprot protein since we need to be sure we have uniprot accessions
    private static final Double NO_RESOLUTION_RETRIEVED_VALUE = 20D;

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
                    if (ProgramVariables.USEINTERNETSOURCES) {
                        try {
                            //TODO if online fetch title from pbd
                            PDBDAO.getPdbInfoForPdbAccession(pdbName);
                        } catch (IOException ex) {
                            FaultBarrier.getInstance().handleException(ex);
                        } catch (XMLStreamException ex) {
                        }
                    }
                }
            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
            }
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
        }

        return pdbData;

    }

    //todo, add domain data for protein to link db
    public List<Domain> getDomainData(Protein aProtein) throws DataRetrievalException {
        List<Domain> foundDomains = new ArrayList<Domain>();
        //try and get from link db, 
        if (foundDomains.isEmpty() && ProgramVariables.USEINTERNETSOURCES) {
            try {
                foundDomains = ExternalDomainFinder.getDomainsForUniprotAccessionFromSingleSource(AccessionConverter.toUniprot(aProtein.getVisibleAccession()), ExternalDomainFinder.DomainWebSites.PFAM);
            } catch (IOException ex) {
                throw new DataRetrievalException(ex.getMessage(), ex);
            } catch (ConversionException ex) {
                throw new DataRetrievalException(ex.getMessage(), ex);
            } catch (XMLStreamException ex) {
                throw new DataRetrievalException(ex.getMessage(), ex);
            }
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

    public Map<Integer, Double> getFreeEnergyForStructure(Protein protein, String pdbAccession) {
        Map<Integer, Double> freeEnergyValues = new HashMap<Integer, Double>();

        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.GetFreeEnergyForStructure());
            stat.setString(1, protein.getVisibleAccession());
            stat.setString(2, pdbAccession);
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    Integer location = rs.getInt("numbering_fasta");
                    try {
                        freeEnergyValues.put(location, Double.parseDouble(rs.getString("E_total")));
                    } catch (NumberFormatException nfe) {
                        freeEnergyValues.put(location, null);
                    }
                }
            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
            }
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
        }

        return freeEnergyValues;
    }

    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(Protein protein, String pdbAccession) {
        Map<Integer, Double> relSasValues = new HashMap<Integer, Double>();

        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getRelativeSolventAccessibilityForStructure());
            stat.setString(1, protein.getVisibleAccession());
            stat.setString(2, pdbAccession);
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    Integer location = rs.getInt("numbering_fasta");
                    try {
                        relSasValues.put(location, Double.parseDouble(rs.getString("SASrel")));
                    } catch (NumberFormatException nfe) {
                        relSasValues.put(location, null);
                    }
                }
            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
            }
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
        }

        return relSasValues;
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

    public StructureDataSource getInstance() {
        return new LinkDb();
    }

    public Set<PdbInfo> getPDBInfoForProtein(Protein protein) {
        Set<PdbInfo> infoSet = new HashSet<PdbInfo>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getLinkDBConnection().prepareStatement(SQLStatements.getPdbInfoForProtein());
            //stat.setString(1, pdbName);
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    PdbInfo info = new PdbInfo();
                    info.setMethod(rs.getString("technique"));
                    info.setPdbAccession(rs.getString("PDB"));
                    info.setName(rs.getString("title"));
                    try {
                        info.setResolution(Double.parseDouble(rs.getString("resolution")));
                    } catch (NumberFormatException nfe) {
                        info.setResolution(NO_RESOLUTION_RETRIEVED_VALUE);
                    }
                    infoSet.add(info);
                }
            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
            }
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
        }
        if (ProgramVariables.USEINTERNETSOURCES && infoSet.isEmpty()) {
            try {
                infoSet.addAll(PDBDAO.getInstance().getPDBInfoForProtein(protein));
            } catch (IOException ex) {
                FaultBarrier.getInstance().handleException(ex);
            } catch (ConversionException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
        return infoSet;
    }

    public Set<PdbInfo> getPdbInforForProtein(Protein protein, Comparator<PdbInfo> sortingComparator) {
        Set<PdbInfo> info = getPDBInfoForProtein(protein);
        if (sortingComparator != null) {
            Ordering<PdbInfo> resolutionOrdering = Ordering.from(sortingComparator);
            info = ImmutableSortedSet.orderedBy(resolutionOrdering).addAll(info).build();

        }
        return info;
    }
}
