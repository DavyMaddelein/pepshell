package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.controllers.objectcontrollers.PeptideGroupController;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
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
 * @author Davy
 */
public class DbDAO extends Observable {

    public static List<Protein> fetchProteins(Experiment project) throws SQLException {
        Protein protToAdd;
        List<Protein> fetchedProteins = new ArrayList<>();
        try (PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllProteins())) {
            stat.setInt(1, project.getExperimentId());
            try (ResultSet rs = stat.executeQuery()) {
                while (rs.next()) {
                    protToAdd = new Protein(rs.getString("accession"));
                    protToAdd.setProjectId(project.getExperimentId());
                    fetchedProteins.add(protToAdd);
                }
            }
        }
        return fetchedProteins;
    }

    public static boolean fetchPeptidesAndProteins(Experiment project) throws SQLException, IOException {
        return fetchPeptidesAndProteins(project, false);
    }

    public static boolean fetchPeptidesAndProteins(Experiment experiment, boolean addQuant) throws SQLException, IOException {
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

    public static void addPeptideGroupsToProteins(List<Protein> proteins) throws SQLException {
        try (PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllPeptidesGrouped())) {
            //obvious location for improving speed if needed
            for (Protein protein : proteins) {
                stat.setInt(1, protein.getProjectid());
                stat.setString(2, protein.getProteinAccession());
                try (ResultSet rs = stat.executeQuery()) {
                    protein.setPeptideGroupsForProtein(PeptideGroupController.createPeptideGroups(rs));
                }
            }
        }
    }
    
    private static void addQuantedPeptideGroupsToProteins(List<Protein> fetchedProteins) throws SQLException {
        try (PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllQuantedPeptideGroups())) {
            for (Protein protein : fetchedProteins) {
                stat.setInt(1, protein.getProjectid());
                stat.setString(2, protein.getProteinAccession());
                try (ResultSet rs = stat.executeQuery()) {
                    protein.setPeptideGroupsForProtein(PeptideGroupController.createPeptideGroups(rs));
                }
            }
        }
    }

    public static List<PeptideGroup> getPeptideGroupsForAccession(String proteinAccession, int projectid) throws SQLException {
        try (PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllPeptidesGroupedForProteinAccession())) {
            stat.setInt(1, projectid);
            stat.setString(2, proteinAccession);
            try (ResultSet rs = stat.executeQuery()) {
                return PeptideGroupController.createPeptideGroups(rs);
            }
        }
    }

    

    public static boolean projectHasQuant(Experiment project) throws SQLException {
        boolean projectHasQuant = true;
        try (PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.quantedCheck())) {
            stat.setInt(1, project.getExperimentId());
            try (ResultSet rs = stat.executeQuery()) {
                if (!rs.next()) {
                    projectHasQuant = false;
                }
            }
        }
        return projectHasQuant;

    }

    public static List<Experiment> getProjects() throws SQLException {
        List<Experiment> projects = new ArrayList<>();
        try (PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllProjects())) {
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                projects.add(new Experiment(rs.getInt("projectid"), rs.getInt("projectid") + " - " + rs.getString("title")));
            }
            rs.close();
        }
        return projects;
    }

    public static Experiment getProject(int projectid, boolean addProteins) throws SQLException, NullPointerException, URISyntaxException, MalformedURLException, IOException {
        Experiment project = null;
        try (PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectASingleProject())) {
            stat.setInt(1, projectid);
            ResultSet rs = stat.executeQuery();
            while (rs.next()) {
                project = new Experiment(projectid, projectid + " - " + rs.getString("title"));
            }
            if (addProteins) {
                DbDAO.fetchPeptidesAndProteins(project);
            }
            rs.close();
        }
        return project;
    }

    public static boolean projectIsQuanted(Experiment project) throws SQLException {
        boolean projectIsQuanted;
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.quantedCheck());
        stat.setInt(1, project.getExperimentId());
        ResultSet rs = stat.executeQuery();
        projectIsQuanted = !rs.isBeforeFirst() || !rs.isAfterLast();
        return projectIsQuanted;
    }
}
