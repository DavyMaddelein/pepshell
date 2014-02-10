package com.compomics.peppi.view.panels;

import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import com.compomics.pepshell.view.panels.JmolPanel;
import org.jmol.adapter.smarter.Structure;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
//import org.biojava

/**
 *
 * @author Davy
 */
public class JmolPanelTest {
    
    public JmolPanelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of preparePDBPanelForProtein method, of class JmolPanel.
     */
    @Test
    public void testPreparePDBPanelForProtein() throws ConversionException {
        System.out.println("preparePDBPanelForProtein");
        Protein protein = null;
        JmolPanel instance = new JmolPanel();
        instance.preparePDBPanelForProtein(protein);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    public void testPDBInit(){
             try {

           //PDBFileReader pdbr = new PDBFileReader();          
          // pdbr.setPath("/Path/To/PDBFiles/");

           String pdbCode = "5pti";

           //Structure struc = pdbr.getStructureById(pdbCode);
          // pdbr

       } catch (Exception e){
           e.printStackTrace();
       }
   }
}