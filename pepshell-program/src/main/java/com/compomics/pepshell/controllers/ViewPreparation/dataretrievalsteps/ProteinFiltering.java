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

import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.controllers.filters.FilterParent;
import com.compomics.pepshell.controllers.filters.NaiveFilter;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.UpdateMessage;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class ProteinFiltering extends DataRetrievalStep {

    private FilterParent<Protein> filter = new NaiveFilter<>();
    private List<Protein> filterList = new ArrayList<>();

    private ProteinFiltering(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public ProteinFiltering() {
    }

    void setFilter(FilterParent<Protein> filter) {
        this.filter = filter;
    }

    public void setFilterList(List<Protein> filterList) {
        this.filterList = filterList;
    }

    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        ProteinFiltering toReturn = new ProteinFiltering(aProteinList);
        toReturn.setFilter(filter);
        toReturn.setFilterList(filterList);
        return toReturn;
    }

    @Override
    public List<Protein> call() throws Exception {

        List<Protein> returnList = proteinList;
        if (!filterList.isEmpty()) {
            this.setChanged();
            this.notifyObservers(new UpdateMessage(false, "filtering protein list",false));
            returnList = filter.filter(proteinList, filterList);
            this.notifyObservers(new UpdateMessage(true, "done filtering",false));

        }
        return returnList;

    }

    @Override
    public String getRetrievalStepDescription() {
        return "Protein Filtering";
    }
}
