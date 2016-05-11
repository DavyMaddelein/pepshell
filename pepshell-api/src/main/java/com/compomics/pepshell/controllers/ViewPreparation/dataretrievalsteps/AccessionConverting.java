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

import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.model.DataModes.DataRetrievalStep;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.UpdateMessage;
import com.compomics.pepshell.model.exceptions.ConversionException;

import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public class AccessionConverting extends DataRetrievalStep {

    private ConversionTo coTo = ConversionTo.TO_UNIPROT;

    public AccessionConverting() {
    }

    private AccessionConverting(List<PepshellProtein> aPepshellProteinList) {
        this.pepshellProteinList = aPepshellProteinList;
    }

    @Override
    public List<PepshellProtein> call() throws Exception {
        //List<PepshellProtein> failedProteins = new ArrayList<>();
        for (PepshellProtein aPepshellProtein : pepshellProteinList) {
                if (coTo == ConversionTo.TO_UNIPROT) {
                    aPepshellProtein.setVisibleAccession(AccessionConverter.toUniprot(aPepshellProtein.getOriginalAccession()));
                    this.setChanged();
                    this.notifyObservers(new UpdateMessage(true, "changed accession of " + aPepshellProtein.getOriginalAccession() + " to " + aPepshellProtein.getVisibleAccession(), false));
                }
        }
        return Collections.unmodifiableList(pepshellProteinList);
    }

    @Override
    public DataRetrievalStep getInstance(List<PepshellProtein> aPepshellProteinList) {
        return new AccessionConverting(aPepshellProteinList);
    }

    public enum ConversionTo {

        TO_UNIPROT

    }

    public void setConversionTo(ConversionTo convertTo) {
        this.coTo = convertTo;
    }

    @Override
    public String getRetrievalStepDescription() {
        return "Convert Accessions";
    }
}
