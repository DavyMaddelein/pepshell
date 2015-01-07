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

/**
 * Created by Davy Maddelein on 04/12/2014.
 */
import com.compomics.pepshell.controllers.dataimport.DataHandlerInterface;
import com.compomics.pepshell.controllers.objectcontrollers.DbConnectionController;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.enums.DataModeEnum;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ImportFromDatabaseHandler<T extends Connection> implements DataHandlerInterface<T> {

    int primaryHandler = -1;
    private volatile List<Integer> secondaryHandlers = new ArrayList<>();

    private List<DataHandlerInterface<T>> concreteDatabaseHandlers = new ArrayList() {
        {
            {
                this.add(new MsLimsDataHandler<T>());
                this.add(new ColimsHandler<T>());
                
            }
        }};


    public ImportFromDatabaseHandler() {
            //getAllDatabaseHandlersFromClassPath()
        }

        @Override
        public boolean canHandle(final T objectToHandle) {
            boolean iCanHandleIt = false;
            try {
                if (objectToHandle != null && objectToHandle.isValid(100)) {
                    //try the possible concrete handlers
                    final ListIterator<DataHandlerInterface<T>> iter = concreteDatabaseHandlers.listIterator();
                    while (iter.hasNext()) {
                        if ((iCanHandleIt = iter.next().canHandle(objectToHandle))) {
                            primaryHandler = iter.previousIndex()+1;
                            new Runnable() {
                                @Override
                                public void run() {
                                    while (iter.hasNext()) {
                                        if (iter.next().canHandle(objectToHandle)) {
                                            secondaryHandlers.add(iter.previousIndex());
                                        }
                                    }
                                }
                            }.run();
                            break;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return iCanHandleIt;
        }

        @Override
        public Experiment addData(Experiment anExperiment) {
            if (primaryHandler > 0 && primaryHandler < concreteDatabaseHandlers.size()) {
                concreteDatabaseHandlers.get(primaryHandler).addData(anExperiment);
            }
            return anExperiment;
        }

    }
