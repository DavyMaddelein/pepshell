package com.compomics.partialtryp.controllers.DAO;

import com.compomics.partialtryp.SQLStatements;
import com.compomics.partialtryp.controllers.objectcontrollers.DbConnectionController;
import com.compomics.partialtryp.model.Project;
import com.compomics.partialtryp.model.QuantedProject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class ProjectDAO {

    public List<Project> getProjects(Connection msLimsConnection) throws SQLException {
        List<Project> projects = new ArrayList<Project>();
        PreparedStatement stat = msLimsConnection.prepareStatement(SQLStatements.selectAllProjects());
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            projects.add(new Project(rs.getInt("projectid"), rs.getInt("projectid") + " - " + rs.getString("title")));
        }
        rs.close();
        stat.close();
        return projects;
    }

    public Project getProject(int projectid, boolean addProteins) throws SQLException, NullPointerException, URISyntaxException, MalformedURLException, IOException {
        Project project = null;
        PreparedStatement stat = DbConnectionController.getConnection().prepareStatement(SQLStatements.selectASingleProject());
        stat.setInt(1, projectid);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            project = new Project(projectid, projectid + " - " + rs.getString("title"));
        }
        if (addProteins) {
            MsLimsDAO.fetchProteins(project);
        }
        rs.close();
        stat.close();
        return project;
    }
}
