/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.mslims.DAO;

import com.compomics.partialtryp.DbSchemeController;
import com.compomics.partialtryp.controllers.DAO.ProjectDAO;
import com.compomics.partialtryp.controllers.objectcontrollers.DbConnectionController;
import com.compomics.partialtryp.model.Project;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

/**
 *
 * @author Davy
 */
public class ProjectDAOTest {
 
    DbConnectionController dbConnection;
    ProjectDAO projects = new ProjectDAO();

    @Before
    public void setup(){
            try {
           DbConnectionController.createConnection("Davy", "aerodynamic", "muppet03.ugent.be", "projects");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @Test
    public Project testProject(){
        Project testProject = null;
        try {
            List<Project> projectList = projects.getProjects(DbConnectionController.getConnection());
            assertThat(DbSchemeController.getDbScheme(),is(DbSchemeController.DbScheme.MSLIMS));
            assertThat(projectList.isEmpty(), is(false));
            testProject = projectList.get(1192);
            assertThat(testProject.getProjectId(),is(1200));
            assertThat(testProject.getProjectName(),is("1200 - shotgun_timeseries WT1"));
            assertThat(testProject.getProteins().isEmpty(),is(true));
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return testProject;
    }   
}
