/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
