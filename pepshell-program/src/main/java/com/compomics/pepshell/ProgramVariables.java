/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.StructureDataSource;
import com.compomics.pepshell.controllers.DataSources.StructureDataSources.LinkDb;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.controllers.dataimport.filevalidation.FileValidatorInterface;
import com.compomics.pepshell.model.enums.DomainWebsitesEnum;
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
    public static DomainWebsitesEnum DOMAINWEBSITE = DomainWebsitesEnum.UNIPROT;
    //database properties
    public static StructureDataSource STRUCTUREDATASOURCE = new LinkDb();
    public static boolean USEINTERNETSOURCES = true;
    public static String CPDTLOCATION;
    public static Set<DataRetrievalStep> loadedPluginSteps = new HashSet<>();
    public static Set<FileValidatorInterface> fileValidators = new HashSet<>();
    public static Set<DrawProteinPeptidesInterface> drawModes = new HashSet<>();
    public static boolean ONLINEMODE = true;
}
