package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.StructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.AbstractDataRetrieval;
import com.compomics.pepshell.model.databases.ExperimentDatabase;

/**
 *
 * @author Davy Maddelein
 */
public abstract class AbstractDataMode {
    
    private AbstractDataRetrieval abstractDataRetrieval;
    private StructureDataSource structureDataSource;
    private ExperimentDatabase experimentDatabase;
    
    
    
    AbstractDataMode(AbstractDataRetrieval aAbstractDataRetrieval, StructureDataSource aStructureDataSource, ExperimentDatabase anExperimentDatabase){
        this.abstractDataRetrieval = aAbstractDataRetrieval;
        this.structureDataSource = aStructureDataSource;
        this.experimentDatabase = anExperimentDatabase;
    }
    
    public AbstractDataRetrieval getViewPreparationForMode(){
        return abstractDataRetrieval;
    }
    
    public AbstractDataRetrieval setViewPreparation(AbstractDataRetrieval aAbstractDataRetrieval){
        this.abstractDataRetrieval = aAbstractDataRetrieval;
        return aAbstractDataRetrieval;
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
