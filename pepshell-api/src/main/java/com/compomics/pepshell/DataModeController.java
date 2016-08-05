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

package com.compomics.pepshell;

import com.compomics.pepshell.controllers.dataimport.databasehandlers.DatabaseDataImportHandler;
import com.compomics.pepshell.controllers.dataimport.filehandlers.FileParserFactory;
import com.compomics.pepshell.controllers.dataimport.DbConnectionController;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.FileBasedExperiment;
import com.compomics.pepshell.model.exceptions.CouldNotParseException;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Davy Maddelein
 */
public final class DataModeController {

    private static DataModeController ourInstance = new DataModeController();

    public static DataModeController getInstance() {
        return ourInstance;
    }

    private DataModeController() {
    }

    private static final DatabaseDataImportHandler<Connection> databaseHandlers = new DatabaseDataImportHandler<>();

    public static void fetchDataToVisualize(Experiment anExperiment) throws DataRetrievalException {
        try {
            if (!DbConnectionController.getExistingConnection().isClosed() && databaseHandlers.canHandle(DbConnectionController.getExistingConnection())) {
                SQLStatements.instantiateLinkDbStatements();
                databaseHandlers.addData(anExperiment);
            } else if (anExperiment instanceof FileBasedExperiment) {
                FileParserFactory.getInstance().parseExperimentFile((FileBasedExperiment) anExperiment);
            }
        } catch (SQLException|CouldNotParseException|IOException sqle) {
            throw new DataRetrievalException("could not retrieve any data for experiment",sqle);
        }
    }
}
