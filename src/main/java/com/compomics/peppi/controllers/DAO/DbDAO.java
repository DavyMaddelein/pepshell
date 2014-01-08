package com.compomics.peppi.controllers.DAO;

import com.compomics.peppi.SQLStatements;
import com.compomics.peppi.controllers.objectcontrollers.DbConnectionController;
import com.compomics.peppi.controllers.objectcontrollers.PeptideGroupController;
import com.compomics.peppi.model.PeptideGroup;
import com.compomics.peppi.model.Project;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.QuantedProject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class DbDAO extends Observable {

    public static boolean fetchProteins(Project project) throws SQLException {
        Protein protToAdd;
        Set<Protein> fetchedProteins = new HashSet<Protein>();
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllProteins());
            stat.setInt(1, project.getProjectId());
            ResultSet rs = stat.executeQuery();
            try {
                while (rs.next()) {
                    protToAdd = new Protein(rs.getString("accession"));
                    protToAdd.setProjectId(project.getProjectId());
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

    public static boolean fetchPeptidesAndProteins(Project project) throws SQLException, IOException {
        fetchProteins(project);
        addPeptideGroupsToProteins(project.getProteins());
        return true;
    }

    public static boolean fetchPeptidesAndProteins(QuantedProject project) throws SQLException, IOException {
        fetchProteins(project);
        addQuantedPeptideGroupsToProteins(project.getProteins());
        project.setProteins(project.getProteins());
        return true;
    }

    private static void addPeptideGroupsToProteins(Set<Protein> proteins) throws SQLException {
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

    private static void addQuantedPeptideGroupsToProteins(Set<Protein> fetchedProteins) throws SQLException {
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

    public static boolean projectHasQuant(Project project) throws SQLException {
        boolean projectHasQuant = true;
        PreparedStatement stat = null;
        try {
            stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.QuantedCheck());
            stat.setInt(1, project.getProjectId());
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

    public static List<Project> getProjects() throws SQLException {
        List<Project> projects = new ArrayList<Project>();
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllProjects());
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            projects.add(new Project(rs.getInt("projectid"), rs.getInt("projectid") + " - " + rs.getString("title")));
        }
        rs.close();
        stat.close();
        return projects;
    }

    public static Project getProject(int projectid, boolean addProteins) throws SQLException, NullPointerException, URISyntaxException, MalformedURLException, IOException {
        Project project = null;
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectASingleProject());
        stat.setInt(1, projectid);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            project = new Project(projectid, projectid + " - " + rs.getString("title"));
        }
        if (addProteins) {
            DbDAO.fetchPeptidesAndProteins(project);
        }
        rs.close();
        stat.close();
        return project;
    }

    public static boolean ProjectIsQuanted(Project project) throws SQLException {
        boolean projectIsQuanted;
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.QuantedCheck());
        stat.setInt(1, project.getProjectId());
        ResultSet rs = stat.executeQuery();
        if (rs.isBeforeFirst() && rs.isAfterLast()) {
            projectIsQuanted = false;
        } else {
            projectIsQuanted = true;
        }
        return projectIsQuanted;
    }
}
