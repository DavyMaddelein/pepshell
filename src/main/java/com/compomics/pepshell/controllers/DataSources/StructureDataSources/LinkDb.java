package com.compomics.pepshell.controllers.DataSources.StructureDataSources;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.PDBDAO;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.controllers.secondarystructureprediction.UniprotSecondaryStructurePrediction;
import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.ProteinInterface;
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
 * @author Davy Maddelein
 * @param <T>
 */
public class LinkDb implements StructureDataSource {
//TODO subtype this to uniprot protein since we need to be sure we have uniprot accessions

    private static final Double NO_RESOLUTION_RETRIEVED_VALUE = 20D;

    public String getPDBDataForPDBName(String pdbName) {
        String pdbData = "no data found";
        PreparedStatement stat;
        try {
            stat = DbConnectionController.getStructDBConnection().prepareStatement(SQLStatements.getPdbDataFromDb());
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
    @Override
    public List<Domain> getDomainData(ProteinInterface aProtein) throws DataRetrievalException {
        List<Domain> foundDomains = new ArrayList<>();
        //try and get from link db, 
        if (foundDomains.isEmpty() && ProgramVariables.USEINTERNETSOURCES) {
            try {
                foundDomains = ExternalDomainFinder.getDomainsForUniprotAccessionFromSingleSource(AccessionConverter.toUniprot(aProtein.getVisibleAccession()), ProgramVariables.DOMAINWEBSITE);
                if (foundDomains.isEmpty()) {
                    foundDomains = ExternalDomainFinder.getDomainsFromAllSitesForUniprotAccession(AccessionConverter.toUniprot(aProtein.getVisibleAccession()));
                }
            } catch (IOException | ConversionException | XMLStreamException ex) {
                throw new DataRetrievalException(ex.getMessage(), ex);
            }
        }
        return foundDomains;
    }

    @Override
    public List<InteractionPartner> getInteractionPartnersForRange(ProteinInterface aProtein, int start, int stop) {
        List<InteractionPartner> interactionPartnerList = new ArrayList<>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getStructDBConnection().prepareStatement(SQLStatements.getInteractionPartnersForRange());
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

    @Override
    public List<InteractionPartner> getInteractionPartners(ProteinInterface aProtein) {
        List<InteractionPartner> interactionPartnerList = new ArrayList<>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getStructDBConnection().prepareStatement(SQLStatements.getInteractionPartnersForResidue());
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

    @Override
    public boolean isAbleToGetFreeEnergy() {
        return true;
    }

    @Override
    public boolean isAbleToGetSolventAccessibility() {
        return true;
    }

    @Override
    public Map<Integer, Double> getFreeEnergyForStructure(ProteinInterface protein, PdbInfo pdbAccession) {
        Map<Integer, Double> freeEnergyValues = new HashMap<>();

        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getStructDBConnection().prepareStatement(SQLStatements.getFreeEnergyForStructure());
            stat.setString(1, protein.getVisibleAccession());
            stat.setString(2, pdbAccession.getPdbAccession());
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
            } catch (SQLException | NullPointerException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
            }
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
        }

        return freeEnergyValues;
    }

    @Override
    public Map<Integer, Double> getRelativeSolventAccessibilityForStructure(ProteinInterface protein, String pdbAccession) {
        Map<Integer, Double> relSasValues = new HashMap<>();

        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getStructDBConnection().prepareStatement(SQLStatements.getRelativeSolventAccessibilityForStructure());
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

    @Override
    public Map<Integer, String> getSecondaryStructureForStructure(ProteinInterface protein, String pdbAccession) {
        Map<Integer, String> secondaryStructureValues = new HashMap<>();

        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getStructDBConnection().prepareStatement(SQLStatements.getSecondaryStructureForStructure());
            stat.setString(1, protein.getVisibleAccession());
            stat.setString(2, pdbAccession);
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    Integer location = rs.getInt("numbering_fasta");
                    String secondaryStructure = rs.getString("secondary_structure");
                    if (!secondaryStructure.isEmpty() || !secondaryStructure.equals("ND")) {
                        secondaryStructureValues.put(location, secondaryStructure);
                    } else {
                        secondaryStructureValues.put(location, null);
                    }
                }
            } catch (SQLException sqle) {
                FaultBarrier.getInstance().handleException(sqle);
            }
        } catch (SQLException sqle) {
            FaultBarrier.getInstance().handleException(sqle);
        }

        if (secondaryStructureValues.isEmpty()
                && ProgramVariables.USEINTERNETSOURCES) {
            try {
                UniprotSecondaryStructurePrediction structurePredictor = new UniprotSecondaryStructurePrediction();
                structurePredictor.getPrediction(AccessionConverter.toUniprot(protein.getVisibleAccession()));
            } catch (IOException | ConversionException ex) {
            }
        }
        return secondaryStructureValues;
    }

    @Override
    public List<InteractionPartner> getInteractionPartnersForPDBName(String pdbName) {
        List<InteractionPartner> interactionPartnerList = new ArrayList<>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getStructDBConnection().prepareStatement(SQLStatements.getInteractionPartnersForPDB());
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

    @Override
    public StructureDataSource getInstance() {
        return new LinkDb();
    }

    Set<PdbInfo> getPDBInfoForProtein(ProteinInterface protein) {
        Set<PdbInfo> infoSet = new HashSet<>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getStructDBConnection().prepareStatement(SQLStatements.getPdbInfoForProtein());
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
            } catch (IOException | ConversionException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
        return infoSet;
    }

    @Override
    public Set<PdbInfo> getPdbInforForProtein(ProteinInterface protein, Comparator<PdbInfo> sortingComparator) {
        Set<PdbInfo> info = getPDBInfoForProtein(protein);
        if (sortingComparator != null) {
            Ordering<PdbInfo> resolutionOrdering = Ordering.from(sortingComparator);
            info = ImmutableSortedSet.orderedBy(resolutionOrdering).addAll(info).build();

        }
        return info;
    }

    @Override
    public boolean isAbleTogetSecondaryStructure() {
        return true;
    }
}
