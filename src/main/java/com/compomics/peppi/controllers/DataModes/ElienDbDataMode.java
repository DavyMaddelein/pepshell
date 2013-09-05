package com.compomics.peppi.controllers.DataModes;

import com.compomics.peppi.controllers.ViewPreparation.PreparationForDbData;

/**
 *
 * @author Davy
 */
public class ElienDbDataMode extends AbstractDataMode{
    
    public ElienDbDataMode(){
        super(new PreparationForDbData());
    }
    
}
