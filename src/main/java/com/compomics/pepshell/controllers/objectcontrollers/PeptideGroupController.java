package com.compomics.pepshell.controllers.objectcontrollers;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.model.MapperInfo;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.QuantedPeptideGroup;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class PeptideGroupController {

    //this should've just been hibernate -.-
    public static List<PeptideGroup> createPeptideGroups(ResultSet rs) throws SQLException {
        return createPeptideGroups(rs, false);
    }

    public static List<PeptideGroup> createPeptideGroups(ResultSet rs, boolean addQuant) throws SQLException {
        //TODO make this check for quantpeptides first, also use comparators and equals for this
        List<PeptideGroup> peptideGroups = new ArrayList<>();
        Map<String, PeptideGroup> peptideGroupHolder = new HashMap<>();
        String peptideSequence;
        double spectrumIntensity = 0.0;

        while (rs.next()) {
            peptideSequence = rs.getString("sequence");
            spectrumIntensity = rs.getDouble("total_spectrum_intensity");
            if (peptideGroupHolder.containsKey(peptideSequence)) {
                peptideGroupHolder.get(peptideSequence).getShortestPeptide().incrementTimesFound();
            } else {
                //do not know if this is wanted behaviour
                if (!isPartiallyPresent(rs, peptideGroupHolder)) {
                    PeptideGroup addedGroup = new PeptideGroup();
                    Peptide peptideToAdd;
                    if (!addQuant) {
                        peptideToAdd = new Peptide(peptideSequence, spectrumIntensity);
                    } else {
                        peptideToAdd = new QuantedPeptide(peptideSequence, spectrumIntensity);
                    }
                    addedGroup.addPeptide(peptideToAdd);
                    checkIfMiscleaved(peptideToAdd);
                    addedGroup.setAlignmentPositions(rs.getInt("start"), rs.getInt("end"));
                    peptideToAdd.setBeginningProteinMatch(rs.getInt("start"));
                    peptideToAdd.setEndProteinMatch(rs.getInt("end"));
                    if (addQuant) {
                        ((QuantedPeptide) peptideToAdd).setRatio(setRatioForPeptide(rs.getInt("identificationid")));
                    }
                    addedGroup.setShortestPeptideIndex(0);
                    //checkForShortestPeptideSequence(peptideToAdd, peptideGroupHolder);
                    peptideGroupHolder.put(peptideSequence, addedGroup);
                }
            }
        }
        rs.beforeFirst();
        peptideGroups.addAll(peptideGroupHolder.values());
        return peptideGroups;
    }

    public static List<QuantedPeptideGroup> createQuantedPeptideGroups(ResultSet rs) throws SQLException {
        List<QuantedPeptideGroup> peptideGroups = new ArrayList<>();
        Map<String, QuantedPeptideGroup> peptideGroupHolder = new HashMap<>();
        String peptideSequence;

        while (rs.next()) {
            peptideSequence = rs.getString("sequence");
            QuantedPeptide peptide = new QuantedPeptide(peptideSequence, rs.getDouble("total_spectrum_intensity"),rs.getDouble("standard_error"));
            if (peptideGroupHolder.containsKey(peptideSequence)) {
                peptideGroupHolder.get(peptideSequence).getShortestPeptide().incrementTimesFound();
            } else {
                checkForShortestPeptideSequence(peptide, peptideGroupHolder);
            }
        }
        rs.beforeFirst();
        peptideGroups.addAll(peptideGroupHolder.values());
        return peptideGroups;
    }

//    /**
//     * this method stops at the first hit found and does not continue further
//     * miss cleavages are mapped randomly to the first partial hit to the keys
//     * of the peptideHolder map
//     *
//     * @param peptideSequence
//     * @param peptideHolder
//     */
//    protected static boolean checkForShortestPeptideSequence(String peptideSequence, Map<String, PeptideGroup> peptideHolder) {
//        //revise this now that peptides have an equals method
//        Iterator<String> peptideSequences = peptideHolder.keySet().iterator();
//        String sequenceInHash;
//        boolean foundInHash = false;
//
//        while (peptideSequences.hasNext()) {
//            sequenceInHash = peptideSequences.next();
//            if (peptideSequence.contains(sequenceInHash)) {
//                peptideHolder.get(sequenceInHash).addPeptide(peptideToAdd);
//                checkIfMiscleaved(peptideToAdd);
//                foundInHash = true;
//                break;
//            } else if (sequenceInHash.contains(peptideSequence)) {
//                //can be added, has been checked if key does not exist in previous method
//                peptideHolder.put(peptideSequence, peptideHolder.get(sequenceInHash));
//                peptideHolder.remove(sequenceInHash);
//                peptideHolder.get(peptideSequence).addPeptide(peptideToAdd);
//                peptideHolder.get(peptideSequence).setShortestPeptideIndex(peptideHolder.get(peptideSequence).getPeptideList().size() - 1);
//                foundInHash = true;
//                break;
//            }
//        }
//        if (!foundInHash) {
//            peptideHolder.put(peptideSequence, new PeptideGroup());
//            peptideHolder.get(peptideSequence).addPeptide(peptideToAdd);
//            peptideHolder.get(peptideSequence).setShortestPeptideIndex(0);
//            peptideHolder.get(peptideSequence).getShortestPeptide().incrementTimesFound();
//            checkIfMiscleaved(peptideToAdd);
//        }
//    }
    //TODO generify for different proteases
    private static void checkIfMiscleaved(Peptide peptide) {
        String peptideSequence = peptide.getSequence().toUpperCase(Locale.getDefault());
        for (int i = 0; i < peptideSequence.length() - 1; i++) {
            if (peptideSequence.charAt(i) == 'K' || peptideSequence.charAt(i) == 'R') {
                peptide.setIsMiscleaved(true);
                break;
            }
        }
    }

    private static void checkForShortestPeptideSequence(QuantedPeptide peptide, Map<String, QuantedPeptideGroup> peptideGroupHolder) {
        Iterator<String> peptideSequences = peptideGroupHolder.keySet().iterator();
        String sequenceInHash;
        boolean foundInHash = false;
        String peptideSequence = peptide.getSequence();
        while (peptideSequences.hasNext()) {
            sequenceInHash = peptideSequences.next();
            if (peptideSequence.contains(sequenceInHash)) {
                peptideGroupHolder.get(sequenceInHash).addPeptide(peptide);
                checkIfMiscleaved(peptide);
                foundInHash = true;
                break;
            } else if (sequenceInHash.contains(peptideSequence)) {
                //can be added, has been checked if key does not exist in previous method
                peptideGroupHolder.put(peptideSequence, peptideGroupHolder.get(sequenceInHash));
                peptideGroupHolder.remove(sequenceInHash);
                peptideGroupHolder.get(peptideSequence).addPeptide(peptide);
                peptideGroupHolder.get(peptideSequence).setShortestPeptideIndex(peptideGroupHolder.get(peptideSequence).getPeptideList().indexOf(peptide) - 1);
                foundInHash = true;
                break;
            }
        }
        if (!foundInHash) {
            peptideGroupHolder.put(peptideSequence, new QuantedPeptideGroup());
            peptideGroupHolder.get(peptideSequence).addPeptide(peptide);
            peptideGroupHolder.get(peptideSequence).setShortestPeptideIndex(0);
            peptideGroupHolder.get(peptideSequence).getShortestPeptide().incrementTimesFound();
            checkIfMiscleaved(peptide);
        }
    }

    public static MapperInfo mapPeptideGroupToProtein(Protein protein, PeptideGroup peptide) throws StringIndexOutOfBoundsException {
        MapperInfo info = new MapperInfo();
        info.setStartingAlignmentPosition(protein.getProteinSequence().indexOf(peptide.getShortestPeptide().getSequence()));
        info.setEndingAlignmentPosition(peptide.getShortestPeptide().getSequence().length());
        return info;
    }

    //this method does too much and must be split out. it also doesn't do what it says it does,
    //it will also get slower the more groups of peptides there are, so prime target is speeding up if needed
    public static boolean isPartiallyPresent(ResultSet rs, Map<String, PeptideGroup> peptideGroupHolder) throws SQLException {
        boolean partiallyPresent = false;
        Iterator<PeptideGroup> peptideGroups = peptideGroupHolder.values().iterator();
        while (peptideGroups.hasNext()) {
            PeptideGroup currentGroup = peptideGroups.next();
            if (rs.getInt("start") >= currentGroup.getStartingAlignmentPosition() && rs.getInt("end") <= currentGroup.getEndAlignmentPosition()) {
                if (rs.getInt("start") > currentGroup.getStartingAlignmentPosition() || rs.getInt("end") < currentGroup.getEndAlignmentPosition()) {
                    Peptide peptideToAdd = new Peptide(rs.getString("sequence"), rs.getDouble("total_spectrum_intensity"));
                    peptideToAdd.setBeginningProteinMatch(rs.getInt("start"));
                    peptideToAdd.setEndProteinMatch(rs.getInt("end"));
                    checkIfMiscleaved(peptideToAdd);
                    peptideGroupHolder.put(rs.getString("sequence"), peptideGroupHolder.get(currentGroup.getShortestPeptide().getSequence()));
                    peptideGroupHolder.remove(currentGroup.getShortestPeptide().getSequence());
                    peptideGroupHolder.get(rs.getString("sequence")).addPeptide(peptideToAdd);
                    peptideGroupHolder.get(rs.getString("sequence")).setShortestPeptideIndex(peptideGroupHolder.get(rs.getString("sequence")).getPeptideList().size() - 1);
                    peptideGroupHolder.get(rs.getString("sequence")).setAlignmentPositions(rs.getInt("start"), rs.getInt("end"));
                } else if (rs.getInt("start") == currentGroup.getStartingAlignmentPosition() && rs.getInt("end") == currentGroup.getEndAlignmentPosition()) {
                    Peptide peptideToAdd = new Peptide(rs.getString("sequence"), rs.getDouble("total_spectrum_intensity"));
                    peptideToAdd.setBeginningProteinMatch(rs.getInt("start"));
                    peptideToAdd.setEndProteinMatch(rs.getInt("end"));
                    checkIfMiscleaved(peptideToAdd);
                    peptideGroupHolder.get(rs.getString("sequence")).addPeptide(peptideToAdd);
                }
                partiallyPresent = true;
                break;
            }
            if (rs.getInt("start") < currentGroup.getStartingAlignmentPosition() && rs.getInt("end") > currentGroup.getEndAlignmentPosition()) {
                Peptide peptideToAdd = new Peptide(rs.getString("sequence"), rs.getDouble("total_spectrum_intensity"));
                peptideToAdd.setBeginningProteinMatch(rs.getInt("start"));
                peptideToAdd.setEndProteinMatch(rs.getInt("end"));
                checkIfMiscleaved(peptideToAdd);
                currentGroup.addPeptide(peptideToAdd);
            }
        }
        return partiallyPresent;
    }

    private static Double setRatioForPeptide(int anIdentificationId) throws SQLException {
        Double ratio = null;
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.getQuantForPeptideIdentifier());
        stat.setInt(1, anIdentificationId);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            ratio = rs.getDouble("ratio");
        }
        return ratio;
    }
}
