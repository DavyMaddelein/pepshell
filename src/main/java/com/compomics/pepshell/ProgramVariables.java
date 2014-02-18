package com.compomics.pepshell;

import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.controllers.DataSources.StructureDataSources.LinkDb;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder;
import java.awt.Color;
import java.io.File;

/**
 *
 * @author Davy
 */
public class ProgramVariables {

    //TODO move all these to their designated preferences
    //viewpreferences
    public static Color PROTEINCOLOR = new Color(93, 100, 202);
    public static Color PEPTIDECOLOR = new Color(223, 156, 55);
    public static Color DOMAINCOLOR = Color.GREEN;
    public static int SCALE = 1;
    public static int VERTICALSIZE = 15;
    public static boolean RESIZEPANELS = true;
    public static File EXPORTFOLDER = new File("C:/Users/Davy");
    public static ExternalDomainFinder.DomainWebSites DOMAINWEBSITE = ExternalDomainFinder.DomainWebSites.UNIPROT;
    //database properties
    public static StructureDataSource STRUCTUREDATASOURCE = new LinkDb();
    public static boolean USEINTERNETSOURCES = true;
}
