package com.compomics.partialtryp.controllers.objectcontrollers;

import com.compomics.partialtryp.model.MapperInfo;
import com.compomics.partialtryp.model.Peptide;
import com.compomics.partialtryp.model.PeptideGroup;
import com.compomics.partialtryp.model.Protein;
import com.compomics.partialtryp.model.QuantedPeptide;
import com.compomics.partialtryp.model.QuantedPeptideGroup;
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

    public static List<PeptideGroup> createPeptideGroups(ResultSet rs) throws SQLException {
        //TODO make this check for quantpeptides first, if nullpointer or sqlexception do normal peptides
        List<PeptideGroup> peptideGroups = new ArrayList<PeptideGroup>();
        Map<String, PeptideGroup> peptideGroupHolder = new HashMap<String, PeptideGroup>();
        String peptideSequence;

        while (rs.next()) {
            peptideSequence = rs.getString("sequence");
            if (peptideGroupHolder.containsKey(peptideSequence)) {
                peptideGroupHolder.get(peptideSequence).getShortestPeptide().incrementTimesFound();
            } else {
                checkForShortestPeptideSequence(peptideSequence, peptideGroupHolder);
            }
        }
        peptideGroups.addAll(peptideGroupHolder.values());
        return peptideGroups;
    }

    public static List<QuantedPeptideGroup> createQuantedPeptideGroups(ResultSet rs) throws SQLException {
        //TODO make this check for quantpeptides first, if nullpointer or sqlexception do normal peptides
        List<QuantedPeptideGroup> peptideGroups = new ArrayList<QuantedPeptideGroup>();
        Map<String, QuantedPeptideGroup> peptideGroupHolder = new HashMap<String, QuantedPeptideGroup>();
        String peptideSequence;

        while (rs.next()) {
            peptideSequence = rs.getString("sequence");
            QuantedPeptide peptide = new QuantedPeptide(peptideSequence, rs.getString("Type"));
            if (peptideGroupHolder.containsKey(peptideSequence)) {
                peptideGroupHolder.get(peptideSequence).getShortestPeptide().incrementTimesFound();
            } else {
                checkForShortestPeptideSequence(peptide, peptideGroupHolder);
            }
        }
        peptideGroups.addAll(peptideGroupHolder.values());
        return peptideGroups;
    }

    /**
     * this method stops at the first hit found and does not continue further
     * miss cleavages are mapped randomly to the first partial hit to the keys
     * of the peptideHolder map TODO when miscleaved check returns true split on
     * miscleaved residue and treat them separately
     *
     * @param peptideSequence
     * @param peptideHolder
     */
    protected static void checkForShortestPeptideSequence(String peptideSequence, Map<String, PeptideGroup> peptideHolder) {
        Iterator<String> peptideSequences = peptideHolder.keySet().iterator();
        Peptide peptideToAdd = new Peptide(peptideSequence);
        String sequenceInHash;
        boolean foundInHash = false;

        while (peptideSequences.hasNext()) {
            sequenceInHash = peptideSequences.next();
            if (peptideSequence.contains(sequenceInHash)) {
                peptideHolder.get(sequenceInHash).add(peptideToAdd);
                checkIfMiscleaved(peptideToAdd);
                foundInHash = true;
                break;
            } else if (sequenceInHash.contains(peptideSequence)) {
                //can be added, has been checked if key does not exist in previous method
                peptideHolder.put(peptideSequence, peptideHolder.get(sequenceInHash));
                peptideHolder.remove(sequenceInHash);
                peptideHolder.get(peptideSequence).add(peptideToAdd);
                peptideHolder.get(peptideSequence).setShortestPeptideIndex(peptideHolder.get(peptideSequence).size() - 1);
                foundInHash = true;
                break;
            }
        }
        if (!foundInHash) {
            peptideHolder.put(peptideSequence, new PeptideGroup());
            peptideHolder.get(peptideSequence).add(peptideToAdd);
            peptideHolder.get(peptideSequence).setShortestPeptideIndex(0);
            peptideHolder.get(peptideSequence).getShortestPeptide().incrementTimesFound();
            checkIfMiscleaved(peptideToAdd);
        }
    }

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
                peptideGroupHolder.get(sequenceInHash).add(peptide);
                checkIfMiscleaved(peptide);
                foundInHash = true;
                break;
            } else if (sequenceInHash.contains(peptideSequence)) {
                //can be added, has been checked if key does not exist in previous method
                peptideGroupHolder.put(peptideSequence, peptideGroupHolder.get(sequenceInHash));
                peptideGroupHolder.remove(sequenceInHash);
                peptideGroupHolder.get(peptideSequence).add(peptide);
                peptideGroupHolder.get(peptideSequence).setShortestPeptideIndex(peptideGroupHolder.get(peptideSequence).size() - 1);
                foundInHash = true;
                break;
            }
        }
        if (!foundInHash) {
            peptideGroupHolder.put(peptideSequence, new QuantedPeptideGroup());
            peptideGroupHolder.get(peptideSequence).add(peptide);
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
}
