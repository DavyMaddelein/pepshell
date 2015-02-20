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

package com.compomics.pepshell.controllers.InfoFinders;

import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.Callable;

/**
 *
 * @author Davy Maddelein
 */
public abstract class DataRetrievalStep extends Observable implements Callable<List<PepshellProtein>> {

    /**
     * the list of proteins to perform the DataRetrievalStep on
     */
    protected List<PepshellProtein> pepshellProteinList = new ArrayList<>();
    /**
     * should the step be executed or not
     */
    private boolean execute;

    /**
     * initialise a DataRetrievalStep
     */
    protected DataRetrievalStep() {
    }

    /**
     * Create an instance of a DataRetrievalStep with a given protein list
     * @param aPepshellProteinList the (@code List) of proteins to process
     */
    public DataRetrievalStep(List<PepshellProtein> aPepshellProteinList) {
        this();
        this.pepshellProteinList = aPepshellProteinList;
    }

    /**
     * returns an instance of the DataRetrievalstep with a given protein (@code List)
     * @param aPepshellProteinList the (@code List) of proteins to process
     * @return an instance of the DataRetrievalStep that will execute on the given protein list
     */
    public abstract DataRetrievalStep getInstance(List<PepshellProtein> aPepshellProteinList);

    /**
     * boolean to determine if the DataRetrievalStep needs to be executed
     * @return if the step needs to be executed or not
     */
    public boolean executeStep() {
        return execute;
    }

    /**
     * set if the DataRetrievalStep needs to be executed or not
     * @param toExecute the boolean indicating execution
     */
    public void setExecute(boolean toExecute) {
        this.execute = toExecute;
    }

    /**
     * a short description of what the DataRetrievalStep does
     * @return a short description of the DataRetrievalStep
     */
    public abstract String getRetrievalStepDescription();

    @Override
    public String toString() {
        return getRetrievalStepDescription();
    }
}
