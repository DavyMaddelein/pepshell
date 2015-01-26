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
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.UpdateMessage;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy Maddelein
 */
public class AccessionMasking extends DataRetrievalStep {

    private final Set<Protein> maskingSet = new HashSet<>();

    private AccessionMasking(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public AccessionMasking() {
    }

    @Override
    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        AccessionMasking toReturn = new AccessionMasking(aProteinList);
        toReturn.setMaskingSet(maskingSet);
        return toReturn;
    }

    @Override
    public List<Protein> call() throws Exception {
        int counter = 0;
        for (Protein maskingProtein : maskingSet) {
            if (proteinList.contains(maskingProtein)) {
                Protein matchedProtein = proteinList.get(proteinList.indexOf(maskingProtein));
                matchedProtein.setVisibleAccession(maskingProtein.getVisibleAccession());
                counter++;
                this.setChanged();
                this.notifyObservers(new UpdateMessage(true,"masked accession of " + matchedProtein.getOriginalAccession() + " with " + matchedProtein.getProteinAccession(),false));
            }
        }
        return Collections.unmodifiableList(proteinList);
    }

    public void setMaskingSet(Set<Protein> aMaskingSet) {
        maskingSet.clear();
        this.maskingSet.addAll(aMaskingSet);
    }

    @Override
    public String getRetrievalStepDescription() {
        return "Accession Masking";
    }
}
