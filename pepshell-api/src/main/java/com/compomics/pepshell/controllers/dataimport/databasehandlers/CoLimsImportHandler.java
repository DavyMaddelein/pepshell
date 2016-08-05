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

package com.compomics.pepshell.controllers.dataimport.databasehandlers;

import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.ViewPreparation.DataRetrievalForFasta;
import com.compomics.pepshell.controllers.ViewPreparation.AbstractDataRetrieval;
import com.compomics.pepshell.model.DataModes.DataHandlerInterface;
import com.compomics.pepshell.controllers.dataimport.DbConnectionController;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Davy Maddelein on 04/12/2014.
 */
public class CoLimsImportHandler<T extends Connection> implements DataHandlerInterface<T> {

    AbstractDataRetrieval<Experiment> colimsAbstractDataRetrieval = new DataRetrievalForFasta<>();


    @Override
    public boolean canHandle(T objectToHandle) throws IOException{
        boolean iCanHandleIt = false;
        try (PreparedStatement stat = DbConnectionController.getExistingConnection().prepareStatement(SQLStatements.CHECKIFCOLIMS)){
            try(ResultSet set = stat.executeQuery()){
            //returns empty
            iCanHandleIt = set.isBeforeFirst() && set.isAfterLast();
            }
        } catch (SQLException e) {
            IOException ex = new IOException("there was a problem with accessing the database");
            ex.addSuppressed(e);
            throw ex;
        }
        return iCanHandleIt;
    }

    @Override
    public Experiment addData(Experiment anExperiment) throws DataRetrievalException {
        return colimsAbstractDataRetrieval.retrieveData(anExperiment);
    }

}
