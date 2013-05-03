package com.compomics.peppi.mslims.DAO;

import com.compomics.peppi.DbSchemeController;
import com.compomics.peppi.controllers.DAO.ProjectDAO;
import com.compomics.peppi.controllers.objectcontrollers.DbConnectionController;
import com.compomics.peppi.model.Project;
import java.sql.SQLException;
import java.util.List;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;
/**
 *
 * @author Davy
 */
public class ProjectDAOTest {

    @Before
    public void setUp() throws SQLException {
            //TODO change to mock db
            DbConnectionController.createConnection("Davy", "aerodynamic", "muppet03.ugent.be", "projects");
                    DbSchemeController.checkDbScheme();
    }
    
    @Test
    public void testProjectFetching() throws SQLException {
        List<Project> projectList = ProjectDAO.getProjects();
        assertThat(projectList.isEmpty(),is(false));
        /**assertThat(projectList.size(),is(15)); will be fixed number after mock db
        assertThat(projectList.get(12).getProjectName(),is(""));
        assertThat(projectList.get(10).getProjectId(),is(1787));
        assertThat(projectList.get(12).getProteins().isEmpty(),is(true));
        **/
    }
}
