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

package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.DataModeController;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.controllers.objectcontrollers.PeptideGroupController;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.protein.proteinimplementations.FlyweightProtein;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.QuantedExperiment;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Davy Maddelein
 */
public class DbDAO extends Observable {

    public static List<PepshellProtein> fetchProteins(Experiment experiment) throws SQLException {
        PepshellProtein protToAdd;
        List<PepshellProtein> fetchedPepshellProteins = new ArrayList<>();
        try (PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(DataModeController.getInstance().getDb().getDataMode().getExperimentDatabase().selectAllProteins())) {
            stat.setInt(1, experiment.getExperimentId());
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    protToAdd = new FlyweightProtein(rs.getString("accession"));
                    protToAdd.setExtraIdentifier(String.valueOf(experiment.getExperimentId()));
                    fetchedPepshellProteins.add(protToAdd);
                }
            }
        }
        return fetchedPepshellProteins;
    }

    public static boolean fetchPeptidesAndProteins(Experiment project) throws SQLException, IOException {
        return fetchPeptidesAndProteins(project, false);
    }

    private static boolean fetchPeptidesAndProteins(Experiment experiment, boolean addQuant) throws SQLException, IOException {
        fetchProteins(experiment);
        if (addQuant) {
            addQuantedPeptideGroupsToProteins(experiment.getProteins());
        } else {
            addPeptideGroupsToProteins(experiment.getProteins());
        }
        return true;
    }

    public static boolean fetchPeptidesAndProteins(QuantedExperiment experiment) throws SQLException, IOException {
        fetchProteins(experiment);
        addQuantedPeptideGroupsToProteins(experiment.getProteins());
        return true;
    }

    public static <E extends PepshellProtein> List<E> addPeptideGroupsToProteins(List<E> proteins) throws SQLException {
        try (PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(DataModeController.getInstance().getDb().getDataMode().getExperimentDatabase().selectAllPeptidesGrouped())) {
            //todo change this to not execute the query per protein
            for (PepshellProtein protein : proteins) {
                stat.setInt(1, Integer.parseInt(protein.getExtraIdentifier()));
                stat.setString(2, protein.getOriginalAccession());
                try (ResultSet rs = stat.executeQuery()) {
                    protein.addPeptideGroups(PeptideGroupController.createPeptideGroups(rs, true));
                }
            }
        }
        return proteins;
    }

    //this could be in one method, and return the quanted peptide groups instead of adding them
    private static <E extends PepshellProtein> List<E> addQuantedPeptideGroupsToProteins(List<E> fetchedProteins) throws SQLException {
        try (PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(DataModeController.getInstance().getDb().getDataMode().getExperimentDatabase().selectAllQuantedPeptideGroups())) {
            //todo change this to not execute the query per protein
            for (E protein : fetchedProteins) {
                stat.setInt(1, Integer.parseInt(protein.getExtraIdentifier()));
                stat.setString(2, protein.getOriginalAccession());
                try (ResultSet rs = stat.executeQuery()) {
                    protein.setPeptideGroupsForProtein(PeptideGroupController.createQuantedPeptideGroups(rs));
                }
            }
        }
        return fetchedProteins;
    }

    public static List<? extends PeptideGroup> getPeptideGroupsForAccession(String proteinAccession, int projectid) throws SQLException {
        try (PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(DataModeController.getInstance().getDb().getDataMode().getExperimentDatabase().selectAllPeptidesGroupedForProteinAccession())) {
            stat.setInt(1, projectid);
            stat.setString(2, proteinAccession);
            try (ResultSet rs = stat.executeQuery()) {
                return PeptideGroupController.createPeptideGroups(rs, true);
            }
        }
    }

    public static boolean projectHasQuant(Experiment anExperiment) throws SQLException {
        boolean projectHasQuant = true;
        try (PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(DataModeController.getInstance().getDb().getDataMode().getExperimentDatabase().ExperimentHasQuant())) {
            stat.setInt(1, anExperiment.getExperimentId());
            try (ResultSet rs = stat.executeQuery()) {
                if (!rs.next()) {
                    projectHasQuant = false;
                }
            }
        }
        return projectHasQuant;

    }

    public static List<? extends Experiment> getExperiments() throws SQLException {
        List<Experiment> experiments = new ArrayList<>();
        try (PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(DataModeController.getInstance().getDb().getDataMode().getExperimentDatabase().selectAllExperiments())) {
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                experiments.add(new Experiment(rs.getInt("projectid"), rs.getInt("projectid") + " - " + rs.getString("title")));
            }
            rs.close();
        }
        return experiments;
    }

    public static Experiment getExperiment(int experimentId, boolean addProteins) throws SQLException, NullPointerException, URISyntaxException, MalformedURLException, IOException {
        Experiment project = null;
        try (PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(DataModeController.getInstance().getDb().getDataMode().getExperimentDatabase().selectASingleExperiment())) {
            stat.setInt(1, experimentId);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                project = new Experiment(experimentId, experimentId + " - " + rs.getString("title"));
            }
            if (addProteins) {
                DbDAO.fetchPeptidesAndProteins(project);
            }
            rs.close();
        }
        return project;
    }

    public static boolean experimentIsQuanted(Experiment project) throws SQLException {
        boolean projectIsQuanted;
        PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(DataModeController.getInstance().getDb().getDataMode().getExperimentDatabase().ExperimentHasQuant());
        stat.setInt(1, project.getExperimentId());
        ResultSet rs = stat.executeQuery();
        projectIsQuanted = !rs.isBeforeFirst() || !rs.isAfterLast();
        return projectIsQuanted;
    }
}
