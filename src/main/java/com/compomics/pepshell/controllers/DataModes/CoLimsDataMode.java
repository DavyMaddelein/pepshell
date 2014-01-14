package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.ExternalStructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.PreparationForDbData;

/**
 *
 * @author Davy
 */
public class CoLimsDataMode extends AbstractDataMode {
    
    public CoLimsDataMode(){
        super(new PreparationForDbData(),new ExternalStructureDataSource());
    }
    
}
