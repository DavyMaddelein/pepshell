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

    public static boolean fetchProteins(Experiment project) throws SQLException {
        Protein protToAdd;
        List<Protein> fetchedProteins = new ArrayList<Protein>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllProteins());
            stat.setInt(1, project.getExperimentId());
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    protToAdd = new Protein(rs.getString("accession"));
                    protToAdd.setProjectId(project.getExperimentId());
                    fetchedProteins.add(protToAdd);
                }
            } finally {
                rs.close();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
        }
        project.setProteins(fetchedProteins);
        return true;
    }

    public static boolean fetchPeptidesAndProteins(Experiment project) throws SQLException, IOException {
        fetchProteins(project);
        addPeptideGroupsToProteins(project.getProteins());
        return true;
    }

    public static boolean fetchPeptidesAndProteins(QuantedExperiment project) throws SQLException, IOException {
        fetchProteins(project);
        addQuantedPeptideGroupsToProteins(project.getProteins());
        project.setProteins(project.getProteins());
        return true;
    }

    public static void addPeptideGroupsToProteins(List<Protein> proteins) throws SQLException {
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllPeptidesGrouped());
            for (Protein protein : proteins) {
                stat.setInt(1, protein.getProjectid());
                stat.setString(2, protein.getProteinAccession());
                ResultSet rs = stat.executeQuery();
                try {
                    protein.setPeptideGroupsForProtein(PeptideGroupController.createPeptideGroups(rs));
                } finally {
                    rs.close();
                }
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
        }
    }

    public static List<PeptideGroup> getPeptideGroupsForAccession(String proteinAccession, int projectid) throws SQLException {
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllPeptidesGroupedForProteinAccession());
            stat.setInt(1, projectid);
            stat.setString(2, proteinAccession);
            ResultSet rs = stat.executeQuery();
            try {
                return PeptideGroupController.createPeptideGroups(rs);
            } finally {
                rs.close();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
        }
    }

    private static void addQuantedPeptideGroupsToProteins(List<Protein> fetchedProteins) throws SQLException {
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllQuantedPeptideGroups());
            for (Protein protein : fetchedProteins) {
                stat.setInt(1, protein.getProjectid());
                stat.setString(2, protein.getProteinAccession());
                ResultSet rs = stat.executeQuery();
                try {
                    protein.setPeptideGroupsForProtein(PeptideGroupController.createPeptideGroups(rs));
                } finally {
                    rs.close();
                }
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
        }
    }

    public static boolean projectHasQuant(Experiment project) throws SQLException {
        boolean projectHasQuant = true;
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.QuantedCheck());
            stat.setInt(1, project.getExperimentId());
            ResultSet rs = stat.executeQuery();
            try {
                if (!rs.next()) {
                    projectHasQuant = false;
                }
            } finally {
                rs.close();
            }
        } finally {
            if (stat != null) {
                stat.close();
            }
        }
        return projectHasQuant;

    }

    public static List<Experiment> getProjects() throws SQLException {
        List<Experiment> projects = new ArrayList<Experiment>();
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllProjects());
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            projects.add(new Experiment(rs.getInt("projectid"), rs.getInt("projectid") + " - " + rs.getString("title")));
        }
        rs.close();
        stat.close();
        return projects;
    }

    public static Experiment getProject(int projectid, boolean addProteins) throws SQLException, NullPointerException, URISyntaxException, MalformedURLException, IOException {
        Experiment project = null;
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectASingleProject());
        stat.setInt(1, projectid);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            project = new Experiment(projectid, projectid + " - " + rs.getString("title"));
        }
        if (addProteins) {
            DbDAO.fetchPeptidesAndProteins(project);
        }
        rs.close();
        stat.close();
        return project;
    }

    public static boolean ProjectIsQuanted(Experiment project) throws SQLException {
        boolean projectIsQuanted;
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.QuantedCheck());
        stat.setInt(1, project.getExperimentId());
        ResultSet rs = stat.executeQuery();
        if (rs.isBeforeFirst() && rs.isAfterLast()) {
            projectIsQuanted = false;
        } else {
            projectIsQuanted = true;
        }
        return projectIsQuanted;
    }
}
