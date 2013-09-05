package com.compomics.peppi.controllers.DataModes;

import com.compomics.peppi.controllers.ViewPreparation.ViewPreparation;

/**
 *
 * @author Davy
 */
public abstract class AbstractDataMode {
    
    private ViewPreparation viewPreparation;
    
    public AbstractDataMode(ViewPreparation aViewPreparation){
        this.viewPreparation = aViewPreparation;
    };
    
    public ViewPreparation getViewPreparationForMode(){
        return viewPreparation;
    }
}
