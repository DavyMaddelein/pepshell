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

import com.compomics.pepshell.controllers.filters.FilterParent;
import com.compomics.pepshell.controllers.filters.NaiveFilter;
import com.compomics.pepshell.model.DataModes.DataRetrievalStep;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.UpdateMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class ProteinFiltering extends DataRetrievalStep {

    private FilterParent<PepshellProtein> filter = new NaiveFilter<>();
    private List<PepshellProtein> filterList = new ArrayList<>();

    private ProteinFiltering(List<PepshellProtein> aPepshellProteinList) {
        this.pepshellProteinList = aPepshellProteinList;
    }

    public ProteinFiltering() {
    }

    void setFilter(FilterParent<PepshellProtein> filter) {
        this.filter = filter;
    }

    public void setFilterList(List<PepshellProtein> filterList) {
        this.filterList = filterList;
    }

    public DataRetrievalStep getInstance(List<PepshellProtein> aPepshellProteinList) {
        ProteinFiltering toReturn = new ProteinFiltering(aPepshellProteinList);
        toReturn.setFilter(filter);
        toReturn.setFilterList(filterList);
        return toReturn;
    }

    @Override
    public List<PepshellProtein> call() throws Exception {

        List<PepshellProtein> returnList = pepshellProteinList;
        if (!filterList.isEmpty()) {
            this.setChanged();
            this.notifyObservers(new UpdateMessage(false, "filtering protein list",false));
            returnList = filter.filter(pepshellProteinList, filterList);
            this.notifyObservers(new UpdateMessage(true, "done filtering",false));

        }
        return returnList;

    }

    @Override
    public String getRetrievalStepDescription() {
        return "PepshellProtein Filtering";
    }
}
