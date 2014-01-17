package com.compomics.pepshell;

import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.controllers.DataSources.StructureDataSources.LinkDb;
import com.compomics.pepshell.controllers.calculators.GradientCalculator;
import com.compomics.pepshell.view.DrawModes.DrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;

/**
 *
 * @author Davy
 */
public class ProgramVariables {

    //TODO move all these to their designated preferences
    //viewpreferences
    public static Color PROTEINCOLOR = Color.RED;
    public static Color PEPTIDECOLOR = Color.BLUE;
    public static Color DOMAINCOLOR = Color.GREEN;
    public static double SCALE = 1;
    public static int VERTICALSIZE = 15;
    public static boolean RESIZEPANELS = true;
    public static DrawModeInterface GLOBALPROTEINDRAWMODE = new StandardPeptideProteinDrawMode();
    public static GradientCalculator PEPTIDEGRADIENTCALCULATOR;
    //database properties
    public static StructureDataSource STRUCTUREDATASOURCE = new LinkDb();
}
