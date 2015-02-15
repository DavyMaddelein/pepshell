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

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.SQLStatements;
import com.compomics.pepshell.controllers.ViewPreparation.DataRetrievalForFasta;
import com.compomics.pepshell.controllers.ViewPreparation.AbstractDataRetrieval;
import com.compomics.pepshell.controllers.dataimport.DataHandlerInterface;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.Experiment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Davy Maddelein on 04/12/2014.
 */
public class ColimsHandler<T extends Connection> implements DataHandlerInterface<T> {

    AbstractDataRetrieval<Experiment> colimsAbstractDataRetrieval = new DataRetrievalForFasta<>();


    @Override
    public boolean canHandle(T objectToHandle) {
        boolean iCanHandleIt = false;
        try {
            PreparedStatement stat = DbConnectionController.getExperimentDbConnection().prepareStatement(SQLStatements.CHECKIFCOLIMS);
            ResultSet set = stat.executeQuery();
            //returns empty
            iCanHandleIt = set.isBeforeFirst() && set.isAfterLast();
        } catch (SQLException e) {
            //obviously we can't
            FaultBarrier.getInstance().handleException(e,true);
        }
        return iCanHandleIt;
    }

    @Override
    public Experiment addData(Experiment anExperiment) {
        return colimsAbstractDataRetrieval.retrieveData(anExperiment);
    }

}
