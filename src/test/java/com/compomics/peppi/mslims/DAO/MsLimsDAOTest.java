package com.compomics.peppi.mslims.DAO;

import com.compomics.peppi.DbSchemeController;
import com.compomics.peppi.controllers.DAO.MsLimsDAO;
import com.compomics.peppi.controllers.objectcontrollers.DbConnectionController;
import com.compomics.peppi.model.PeptideGroup;
import com.compomics.peppi.model.Project;
import com.compomics.peppi.model.QuantedProject;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.List;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 *
 * @author Davy
 */
public class MsLimsDAOTest {

    private static Project testProject;
    private static Project quantedTestProject;

    @Before
    public void setUp() throws SQLException {
       //TODO change to mock db
        DbConnectionController.createConnection("Davy", "aerodynamic", "muppet03.ugent.be", "projects");
        DbSchemeController.checkDbScheme();
        testProject = new Project(1200, "mockProject");
        quantedTestProject = new QuantedProject(1640, "quantedMockProject");

    }

    @Test
    public void testIdentificationDAO() throws SQLException, URISyntaxException, URISyntaxException, MalformedURLException, IOException {
        MsLimsDAO.fetchPeptidesAndProteins(testProject);
        assertThat(testProject.getProteins().size(), is(1549));
        assertThat(testProject.getProteins().get(548).getProteinAccession(), is("AT2G29450.1"));
        assertThat(testProject.getProteins().get(548).getProjectid(),is(testProject.getProjectId()));
        //assertThat(testProject.getProteins().get(548).getProteinSequence(), is("")); to WebDAOTest
        assertThat(testProject.getProteins().get(466).getPeptideGroupsForProtein().size(), is(2));
        assertThat(testProject.getProteins().get(466).getPeptideGroupsForProtein().get(0).getShortestPeptide().getSequence(), is("IGLGDPAVNK"));
    
        //TODO add for quantedProject
        MsLimsDAO.fetchPeptidesAndProteins(quantedTestProject);
    }

    @Test
    public void testGetPeptideGroups() throws SQLException{
            List<PeptideGroup> peptideGroups = MsLimsDAO.getPeptideGroupsForAccession("AT4G24830.1", testProject.getProjectId());
            assertThat(peptideGroups.isEmpty(),is(false));
            assertThat(peptideGroups.size(),is(8));
            assertThat(peptideGroups.get(0).getShortestPeptide().getSequence(), is("YLLGTSMARPVIAK"));
            assertThat(peptideGroups.get(1).getShortestPeptide().getTimesFound(), is(1));
            
            peptideGroups = MsLimsDAO.getPeptideGroupsForAccession("gi|195991534",quantedTestProject.getProjectId());
            assertThat(peptideGroups.isEmpty(),is(false));
            assertThat(peptideGroups.size(),is(2));
    }
    
    
    @Test
    public void testQuant() throws SQLException {
        
        assertThat(MsLimsDAO.projectHasQuant(testProject), is(true));
        assertThat(MsLimsDAO.projectHasQuant(quantedTestProject), is(true));
    }
}