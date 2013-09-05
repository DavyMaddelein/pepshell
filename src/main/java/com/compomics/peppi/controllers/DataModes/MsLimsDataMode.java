package com.compomics.peppi.controllers.DataModes;

import com.compomics.peppi.controllers.ViewPreparation.PreparationForDbData;

/**
 *
 * @author Davy
 */
public class MsLimsDataMode extends AbstractDataMode {

    public MsLimsDataMode() {
        super(new PreparationForDbData());
    }
}
