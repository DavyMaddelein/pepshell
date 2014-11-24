package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.ViewPreparation;
import com.compomics.pepshell.model.databases.ExperimentDatabase;

/**
 *
 * @author Davy Maddelein
 */
public abstract class AbstractDataMode {
    
    private ViewPreparation viewPreparation;
    private StructureDataSource structureDataSource;
    private ExperimentDatabase experimentDatabase;
    
    
    
    AbstractDataMode(ViewPreparation aViewPreparation, StructureDataSource aStructureDataSource, ExperimentDatabase anExperimentDatabase){
        this.viewPreparation = aViewPreparation;
        this.structureDataSource = aStructureDataSource;
        this.experimentDatabase = anExperimentDatabase;
    }
    
    public ViewPreparation getViewPreparationForMode(){
        return viewPreparation;
    }
    
    public ViewPreparation setViewPreparation(ViewPreparation aViewPreparation){
        this.viewPreparation = aViewPreparation;
        return aViewPreparation;
    }
    
    public StructureDataSource getStructureDataSource(){
        return structureDataSource;
    }
    
    public void setStructureDataSource(StructureDataSource aStructureDataSource){
        this.structureDataSource = aStructureDataSource;
    }
    
    public ExperimentDatabase getExperimentDatabase(){
        return experimentDatabase;
    }
    
    public void setExperimentDatabase(ExperimentDatabase anExperimentDatabase){
        this.experimentDatabase = anExperimentDatabase;
    }
}
