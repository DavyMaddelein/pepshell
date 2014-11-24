package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.InternetStructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.PreparationForDbData;
import com.compomics.pepshell.model.databases.MsLimsExperimentDatabase;

/**
 *
 * @author Davy Maddelein
 */
public class MsLimsDataMode extends AbstractDataMode {

    public MsLimsDataMode() {
        super(new PreparationForDbData(), new InternetStructureDataSource(), new MsLimsExperimentDatabase());
    }
}
