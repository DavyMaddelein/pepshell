/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshellmslimsconnector.tempholder;

import com.compomics.pepshell.model.*;
import com.compomics.pepshell.model.databases.DbDAOInterface;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Davy Maddelein
 */
public class MsLimsDbDAO extends Observable implements DbDAOInterface {

    private MsLimsExperimentDatabase db = new MsLimsExperimentDatabase();

    @Override
    public <T extends Experiment> List<PepshellProtein> fetchProteins(T experiment, Connection aConnection) throws SQLException {
        PepshellProtein protToAdd;
        List<PepshellProtein> fetchedPepshellProteins = new ArrayList<>();
        try (PreparedStatement stat = aConnection.prepareStatement(db.selectAllProteins())) {
            stat.setInt(1, experiment.getExperimentId());
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    protToAdd = new PepshellProtein(rs.getString("accession"));
                    protToAdd.setExtraIdentifier(String.valueOf(experiment.getExperimentId()));
                    fetchedPepshellProteins.add(protToAdd);
                }
            }
        }
        return fetchedPepshellProteins;
    }

    @Override
    public Experiment fetchPeptidesAndProteins(Experiment experiment, Connection aConnection) throws SQLException, IOException {
        this.fetchProteins(experiment, aConnection);
        if (experiment instanceof QuantedExperiment) {
            addQuantedPeptideGroupsToProteins(experiment.getProteins(), aConnection);
        } else {
            addPeptideGroupsToProteins(experiment.getProteins(), aConnection);
        }
        return experiment;
    }

    @Override
    public <E extends PepshellProtein> List<E> addPeptideGroupsToProteins(List<E> proteins, Connection aConnection) throws SQLException {
        try (PreparedStatement stat = aConnection.prepareStatement(db.selectAllPeptidesGrouped())) {
            //todo change this to not execute the query per protein
            for (PepshellProtein protein : proteins) {
                stat.setInt(1, Integer.parseInt(protein.getExtraIdentifier()));
                stat.setString(2, protein.getOriginalAccession());
                try (ResultSet rs = stat.executeQuery()) {
                    protein.addPeptideGroups(createPeptideGroups(rs));
                }
            }
        }
        return proteins;
    }

    //this could be in one method, and return the quanted peptide groups instead of adding them
    private <E extends PepshellProtein> List<E> addQuantedPeptideGroupsToProteins(List<E> fetchedProteins, Connection aConnection) throws SQLException {
        try (PreparedStatement stat = aConnection.prepareStatement(db.selectAllQuantedPeptideGroups())) {
            //todo change this to not execute the query per protein
            for (E protein : fetchedProteins) {
                stat.setInt(1, Integer.parseInt(protein.getExtraIdentifier()));
                stat.setString(2, protein.getOriginalAccession());
                try (ResultSet rs = stat.executeQuery()) {
                    protein.setPeptideGroupsForProtein(createQuantedPeptideGroups(rs));
                }
            }
        }
        return fetchedProteins;
    }


    // FIXME: 3/22/2016 rewrite this to get quant decently?
    @Override
    public List<? extends PeptideGroup> getPeptideGroupsForAccession(String proteinAccession, int projectid, Connection aConnection) throws SQLException {
        try (PreparedStatement stat = aConnection.prepareStatement(db.selectAllPeptidesGroupedForProteinAccession())) {
            stat.setInt(1, projectid);
            stat.setString(2, proteinAccession);
            List<PeptideGroup> groups = new ArrayList<>();
            try (ResultSet rs = stat.executeQuery()) {
                for(PeptideGroup aPeptideGroupList : createPeptideGroups(rs)){
                    PeptideGroup quantedGroup = null;
                    for(PeptideInterface aPeptide : aPeptideGroupList.getPeptideList()){
                        QuantedPeptide qpeptide = new QuantedPeptide(aPeptide.getSequence());
                        if(quantedGroup == null){
                            quantedGroup = new PeptideGroup(qpeptide);
                        }
                        qpeptide.setRatio(getRatioForPeptide(rs.getInt("identificationid"), aConnection));
                        qpeptide.setStandardError(setErrorForPeptide(rs.getInt("identificationid"), aConnection));
                    }
                    if(groups != null){
                        //this makes us lose all the non quanted peptides
                    groups.add(quantedGroup);
                    }
                }
            }
            return groups;
        }
    }

    @Override
    public boolean projectHasQuant(Experiment anExperiment, Connection aConnection) throws SQLException {
        boolean projectHasQuant = true;
        try (PreparedStatement stat = aConnection.prepareStatement(db.ExperimentHasQuant())) {
            stat.setInt(1, anExperiment.getExperimentId());
            try (ResultSet rs = stat.executeQuery()) {
                if (!rs.next()) {
                    projectHasQuant = false;
                }
            }
        }
        return projectHasQuant;

    }

    @Override
    public List<? extends Experiment> getExperiments(Connection aConnection) throws SQLException {
        List<Experiment> experiments = new ArrayList<>();
        try (PreparedStatement stat = aConnection.prepareStatement(db.selectAllExperiments())) {
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                experiments.add(new Experiment(rs.getInt("projectid"), rs.getInt("projectid") + " - " + rs.getString("title")));
            }
            rs.close();
        }
        return experiments;
    }

    @Override
    public Experiment getExperiment(int experimentId, boolean addProteins, Connection aConnection) throws SQLException, NullPointerException, URISyntaxException, MalformedURLException, IOException {
        Experiment experiment = null;
        try (PreparedStatement stat = aConnection.prepareStatement(db.selectASingleExperiment())) {
            stat.setInt(1, experimentId);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                experiment = new Experiment(experimentId, experimentId + " - " + rs.getString("title"));
            }
            if (addProteins) {
                fetchPeptidesAndProteins(experiment, aConnection);
            }
            rs.close();
        }
        return experiment;
    }

    @Override
    public boolean experimentIsQuanted(Experiment project, Connection aConnection) throws SQLException {
        boolean projectIsQuanted;
        PreparedStatement stat = aConnection.prepareStatement(db.ExperimentHasQuant());
        stat.setInt(1, project.getExperimentId());
        ResultSet rs = stat.executeQuery();
        projectIsQuanted = !rs.isBeforeFirst() || !rs.isAfterLast();
        return projectIsQuanted;
    }

    @Override
    public Double getRatioForPeptide(int anIdentificationId, Connection aConnection) throws SQLException {
        Double ratio = null;
        PreparedStatement stat = aConnection.prepareStatement(db.getQuantForPeptide());
        stat.setInt(1, anIdentificationId);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            ratio = rs.getDouble("ratio");
        }
        return ratio;
    }

    @Override
    public Double setErrorForPeptide(int anIdentificationId, Connection aConnection) throws SQLException {
        Double ratio = null;
        PreparedStatement stat = aConnection.prepareStatement(db.getErrorForQuantedPeptide());
        stat.setInt(1, anIdentificationId);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            ratio = rs.getDouble("standard_error");
        }
        return ratio;
    }

    private List<PeptideGroup> createPeptideGroups(ResultSet rs) throws SQLException {
        //TODO make this check for quantpeptides first, also use comparators and equals for this
        List<PeptideGroup> peptideGroups = new ArrayList<>();
        Map<String, PeptideGroup> peptideGroupHolder = new HashMap<>();
        String peptideSequence;
        double spectrumIntensity;

        while (rs.next()) {
            peptideSequence = rs.getString("sequence");
            spectrumIntensity = rs.getDouble("total_spectrum_intensity");
            if (peptideGroupHolder.containsKey(peptideSequence)) {
                peptideGroupHolder.get(peptideSequence).getRepresentativePeptide().incrementTimesFound();
            } else {
                //do not know if this is wanted behaviour, could be replaced with an option
                if (!isPartiallyPresent(rs, peptideGroupHolder)) {
                    Peptide peptideToAdd = new Peptide(peptideSequence, spectrumIntensity);
                    PeptideGroup addedGroup = new PeptideGroup(peptideToAdd);
                    addedGroup.addPeptide(peptideToAdd);
                    checkIfMiscleaved(peptideToAdd);
                    addedGroup.setAlignmentPositions(rs.getInt("start"), rs.getInt("end"));
                    peptideToAdd.setBeginningProteinMatch(rs.getInt("start"));
                    peptideToAdd.setEndProteinMatch(rs.getInt("end"));
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

    private List<QuantedPeptideGroup> createQuantedPeptideGroups(ResultSet rs) throws SQLException {
        List<QuantedPeptideGroup> peptideGroups = new ArrayList<>();
        Map<String, QuantedPeptideGroup> peptideGroupHolder = new HashMap<>();
        String peptideSequence;

        while (rs.next()) {
            peptideSequence = rs.getString("sequence");
            QuantedPeptide peptide = new QuantedPeptide(peptideSequence, rs.getDouble("total_spectrum_intensity"), rs.getDouble("ratio"), rs.getDouble("standard_error"));
            if (peptideGroupHolder.containsKey(peptideSequence)) {
                peptideGroupHolder.get(peptideSequence).getRepresentativePeptide().incrementTimesFound();
            } else {
                checkForShortestPeptideSequence(peptide, peptideGroupHolder);
            }
        }
        rs.beforeFirst();
        peptideGroups.addAll(peptideGroupHolder.values());
        return peptideGroups;
    }

    //this method does too much and must be split out. it also doesn't do what it says it does,
    //it will also get slower the more groups of peptides there are, so prime target is speeding up if needed
    private boolean isPartiallyPresent(ResultSet rs, Map<String, PeptideGroup> peptideGroupHolder) throws SQLException {
        boolean partiallyPresent = false;
        for (PeptideGroup currentGroup : peptideGroupHolder.values()) {
            if (rs.getInt("start") >= currentGroup.getStartingAlignmentPosition() && rs.getInt("end") <= currentGroup.getEndAlignmentPosition()) {
                if (rs.getInt("start") > currentGroup.getStartingAlignmentPosition() || rs.getInt("end") < currentGroup.getEndAlignmentPosition()) {
                    Peptide peptideToAdd = new QuantedPeptide(rs.getString("sequence"), rs.getDouble("total_spectrum_intensity"));
                    peptideToAdd.setBeginningProteinMatch(rs.getInt("start"));
                    peptideToAdd.setEndProteinMatch(rs.getInt("end"));
                    checkIfMiscleaved(peptideToAdd);
                    peptideGroupHolder.put(rs.getString("sequence"), peptideGroupHolder.get(currentGroup.getRepresentativePeptide().getSequence()));
                    peptideGroupHolder.remove(currentGroup.getRepresentativePeptide().getSequence());
                    peptideGroupHolder.get(rs.getString("sequence")).addPeptide(peptideToAdd);
                    peptideGroupHolder.get(rs.getString("sequence")).setShortestPeptideIndex(peptideGroupHolder.get(rs.getString("sequence")).getPeptideList().size() - 1);
                    peptideGroupHolder.get(rs.getString("sequence")).setAlignmentPositions(rs.getInt("start"), rs.getInt("end"));
                } else if (rs.getInt("start") == currentGroup.getStartingAlignmentPosition() && rs.getInt("end") == currentGroup.getEndAlignmentPosition()) {
                    Peptide peptideToAdd = new QuantedPeptide(rs.getString("sequence"), rs.getDouble("total_spectrum_intensity"));
                    peptideToAdd.setBeginningProteinMatch(rs.getInt("start"));
                    peptideToAdd.setEndProteinMatch(rs.getInt("end"));
                    checkIfMiscleaved(peptideToAdd);
                    peptideGroupHolder.get(rs.getString("sequence")).addPeptide(peptideToAdd);
                }
                partiallyPresent = true;
                break;
            }
            if (rs.getInt("start") < currentGroup.getStartingAlignmentPosition() && rs.getInt("end") > currentGroup.getEndAlignmentPosition()) {
                Peptide peptideToAdd = new QuantedPeptide(rs.getString("sequence"), rs.getDouble("total_spectrum_intensity"));
                peptideToAdd.setBeginningProteinMatch(rs.getInt("start"));
                peptideToAdd.setEndProteinMatch(rs.getInt("end"));
                checkIfMiscleaved(peptideToAdd);
                currentGroup.addPeptide(peptideToAdd);
            }
        }
        return partiallyPresent;
    }

    //TODO generify for different proteases
    private void checkIfMiscleaved(Peptide peptide) {
        String peptideSequence = peptide.getSequence().toUpperCase(Locale.getDefault());
        for (int i = 0; i < peptideSequence.length() - 1; i++) {
            if (peptideSequence.charAt(i) == 'K' || peptideSequence.charAt(i) == 'R') {
                peptide.setIsMiscleaved(true);
                break;
            }
        }
    }

    private void checkForShortestPeptideSequence(QuantedPeptide peptide, Map<String, QuantedPeptideGroup> peptideGroupHolder) {
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
            peptideGroupHolder.put(peptideSequence, new QuantedPeptideGroup(peptide));
            peptideGroupHolder.get(peptideSequence).setShortestPeptideIndex(0);
            peptideGroupHolder.get(peptideSequence).getRepresentativePeptide().incrementTimesFound();
            checkIfMiscleaved(peptide);
        }
    }
}
