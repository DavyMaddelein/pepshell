package com.compomics.peppi.controllers.DAO;

import com.compomics.peppi.SQLStatements;
import com.compomics.peppi.controllers.objectcontrollers.DbConnectionController;
import com.compomics.peppi.model.Project;
import java.io.IOException;
import java.net.MalformedURLException;
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
public class ProjectDAO {

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
            MsLimsDAO.fetchPeptidesAndProteins(project);
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
        if (rs.isBeforeFirst() && rs.isAfterLast()){
            projectIsQuanted = false;
        } else{
            projectIsQuanted = true;
        }
        return projectIsQuanted;
    }
}
