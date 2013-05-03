package com.compomics.peppi.controllers.DAO;

import com.compomics.peppi.SQLStatements;
import com.compomics.peppi.controllers.objectcontrollers.DbConnectionController;
import com.compomics.peppi.controllers.objectcontrollers.PeptideGroupController;
import com.compomics.peppi.model.PeptideGroup;
import com.compomics.peppi.model.Project;
import com.compomics.peppi.model.Protein;
import com.compomics.peppi.model.QuantedProject;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class MsLimsDAO {

    public static void fetchProteins(Project project) throws SQLException, URISyntaxException, IOException {
        Protein protToAdd;
        List<Protein> fetchedProteins = new ArrayList<Protein>();
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllProteins());
        stat.setInt(1, project.getProjectId());
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            protToAdd = new Protein(rs.getString("accession"));
            protToAdd.setProjectId(project.getProjectId());
            fetchedProteins.add(protToAdd);
            //protToAdd.addPdbFileNames(PDBDAO.getAllPDBFileNamesForProtein(protToAdd));
        }
        addPeptideGroupsToProteins(fetchedProteins);
        rs.close();
        stat.close();
        project.setProteins(fetchedProteins);
    }

    public static void fetchProteins(QuantedProject project) throws SQLException, URISyntaxException, IOException {
        Protein protToAdd;
        List<Protein> fetchedProteins = new ArrayList<Protein>();
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllProteins());
        stat.setInt(1, project.getProjectId());
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            protToAdd = new Protein(rs.getString("accession"));
            protToAdd.setProjectId(project.getProjectId());
            fetchedProteins.add(protToAdd);
            //protToAdd.addPdbFileNames(PDBDAO.getAllPDBFileNamesForProtein(protToAdd));
        }
        addQuantedPeptideGroupsToProteins(fetchedProteins);
        rs.close();
        stat.close();
        project.setProteins(fetchedProteins);
    }

    private static void addPeptideGroupsToProteins(List<Protein> proteins) throws SQLException {
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllPeptidesGrouped());
        for (Protein protein : proteins) {
            stat.setInt(1, protein.getProjectid());
            stat.setString(2, protein.getProteinAccession());
            ResultSet rs = stat.executeQuery();
            protein.setPeptideGroupsForProtein(PeptideGroupController.createPeptideGroups(rs));
            rs.close();
        }
        stat.close();
    }

    public static List<PeptideGroup> getPeptideGroupsForAccession(String proteinAccession, int projectid) throws SQLException {
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllPeptidesGroupedForProteinAccession());
        stat.setInt(1, projectid);
        stat.setString(2, proteinAccession);
        ResultSet rs = stat.executeQuery();
        try {
            return PeptideGroupController.createPeptideGroups(rs);
        } finally {
            rs.close();
            stat.close();
        }
    }

    private static void addQuantedPeptideGroupsToProteins(List<Protein> fetchedProteins) throws SQLException {
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectAllQuantedPeptideGroups());
        for (Protein protein : fetchedProteins) {
            stat.setInt(1, protein.getProjectid());
            stat.setString(2, protein.getProteinAccession());
            ResultSet rs = stat.executeQuery();
            protein.setPeptideGroupsForProtein(PeptideGroupController.createPeptideGroups(rs));
            rs.close();
        }
        stat.close();
    }

    public static boolean projectHasQuant(Project project) {
        boolean projectHasQuant = false;

        return projectHasQuant;

    }
}
