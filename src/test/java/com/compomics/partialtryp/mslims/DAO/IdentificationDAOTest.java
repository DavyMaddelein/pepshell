/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.mslims.DAO;

import com.compomics.partialtryp.controllers.DAO.MsLimsDAO;
import com.compomics.partialtryp.controllers.DAO.ProjectDAO;
import com.compomics.partialtryp.controllers.objectcontrollers.DbConnectionController;
import com.compomics.partialtryp.model.Project;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Davy
 */
public class IdentificationDAOTest {

    Project testProject;

    @Before
    public void setup() throws NullPointerException, URISyntaxException, MalformedURLException, MalformedURLException, IOException, SQLException {

            DbConnectionController.createConnection("Davy", "aerodynamic", "muppet03.ugent.be", "projects");
            ProjectDAO projects = new ProjectDAO();
            testProject = projects.getProject(1200,true);

    }

    @Test
    public void testIdentificationDAO() throws SQLException, URISyntaxException, URISyntaxException, MalformedURLException, IOException {
        MsLimsDAO.fetchProteins(testProject);
        assertThat(testProject.getProteins().size(), is(1549));
        assertThat(testProject.getProteins().get(548).getProteinAccession(),is("AT2G29450.1"));
        assertThat(testProject.getProteins().get(466).getPeptideGroupsForProtein().size(), is(2));
        assertThat(testProject.getProteins().get(466).getPeptideGroupsForProtein().get(0).getShortestPeptide().getSequence(),is(""));
    }
}