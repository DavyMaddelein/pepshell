package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.InternetStructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.PreparationForDbData;

/**
 *
 * @author Davy
 */
public class MsLimsDataMode extends AbstractDataMode {

    public MsLimsDataMode() {
        super(new PreparationForDbData(),new InternetStructureDataSource());
    }
}
