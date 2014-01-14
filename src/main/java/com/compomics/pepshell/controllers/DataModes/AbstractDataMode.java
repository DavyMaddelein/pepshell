package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.ViewPreparation;

/**
 *
 * @author Davy
 */
public abstract class AbstractDataMode {
    
    private ViewPreparation viewPreparation;
    private StructureDataSource structureDataSource;
    
    public AbstractDataMode(ViewPreparation aViewPreparation,StructureDataSource aStructureDataSource){
        this.viewPreparation = aViewPreparation;
        this.structureDataSource = aStructureDataSource;
    };
    
    public ViewPreparation getViewPreparationForMode(){
        return viewPreparation;
    }
    
    public void setViewPreparation(ViewPreparation aViewPreparation){
        this.viewPreparation = aViewPreparation;
    }
    
    public StructureDataSource getStructureDataSource(){
        return structureDataSource;
    }
}
