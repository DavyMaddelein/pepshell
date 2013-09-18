package com.compomics.peppi.controllers.DataModes;

import com.compomics.peppi.controllers.ViewPreparation.PreparationForDbData;

/**
 *
 * @author Davy
 */
public class CoLimsDataMode extends AbstractDataMode {
    
    public CoLimsDataMode(){
        super(new PreparationForDbData());
    }
    
}
