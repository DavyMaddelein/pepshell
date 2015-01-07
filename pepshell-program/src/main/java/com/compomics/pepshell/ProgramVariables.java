package com.compomics.pepshell;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.StructureDataSource;
import com.compomics.pepshell.controllers.DataSources.StructureDataSources.LinkDb;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.controllers.InfoFinders.ExternalDomainFinder;
import com.compomics.pepshell.controllers.dataimport.filevalidation.FileValidatorInterface;
import com.compomics.pepshell.view.DrawModes.DrawProteinPeptidesInterface;

import java.awt.Color;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Davy Maddelein
 */
public class ProgramVariables {

    //TODO move all these to their designated preferences
    //viewpreferences
    public static final Color PROTEINCOLOR = new Color(93, 100, 202);
    public static final Color PEPTIDECOLOR = new Color(223, 156, 55);
    public static final Color DOMAINCOLOR = Color.GREEN;
    public static int SCALE = 1;
    public static final int VERTICALSIZE = 15;
    public static boolean RESIZEPANELS = true;
    //program properties
    public static File EXPORTFOLDER = new File("C:/Users/Davy");
    public static ExternalDomainFinder.DomainWebSites DOMAINWEBSITE = ExternalDomainFinder.DomainWebSites.UNIPROT;
    //database properties
    public static StructureDataSource STRUCTUREDATASOURCE = new LinkDb();
    public static boolean USEINTERNETSOURCES = true;
    public static String CPDTLOCATION;
    public static Set<DataRetrievalStep> loadedPluginSteps = new HashSet<>();
    public static Set<FileValidatorInterface> fileValidators = new HashSet<>();
    public static Set<DrawProteinPeptidesInterface> drawModes = new HashSet<>();
}
