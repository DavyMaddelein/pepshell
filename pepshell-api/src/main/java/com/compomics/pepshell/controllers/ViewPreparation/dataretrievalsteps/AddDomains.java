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

package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.DataModes.DataRetrievalStep;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.UpdateMessage;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;

import java.net.UnknownHostException;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class AddDomains extends DataRetrievalStep {

    private AddDomains(List<PepshellProtein> aPepshellProteinList) {
        this.pepshellProteinList = aPepshellProteinList;
    }

    public AddDomains() {
    }

    @Override
    public DataRetrievalStep getInstance(List<PepshellProtein> aPepshellProteinList) {
        return new AddDomains(aPepshellProteinList);
    }

    @Override
    public List<PepshellProtein> call() throws Exception {
        for (PepshellProtein pepshellProtein : pepshellProteinList) {
            try {
                pepshellProtein.addDomains(ProgramVariables.STRUCTUREDATASOURCE.getDomainData(pepshellProtein));
                this.setChanged();
                this.notifyObservers(new UpdateMessage(true, "added domain info to " + pepshellProtein.getVisibleAccession(), false));
            } catch (DataRetrievalException ex) {
                FaultBarrier.getInstance().handleException(ex);
                if (ex.getCause() != null && ex.getCause() instanceof UnknownHostException){
                    ProgramVariables.USEINTERNETSOURCES = false;
                    break;
                }
            }
        }
        return Collections.unmodifiableList(pepshellProteinList);
    }

    @Override
    public String getRetrievalStepDescription() {
        return "Add Domains";
    }
}
