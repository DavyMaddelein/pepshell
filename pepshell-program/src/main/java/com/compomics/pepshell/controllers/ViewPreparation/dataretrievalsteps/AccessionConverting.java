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
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
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

    private AccessionConverting(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    @Override
    public List<Protein> call() throws Exception {
        for (Protein aProtein : proteinList) {
            try {
                if (coTo == ConversionTo.TO_UNIPROT) {
                    aProtein.setAccession(AccessionConverter.toUniprot(aProtein.getOriginalAccession()));
                    this.setChanged();
                    this.notifyObservers(new UpdateMessage(true, "changed accession of " + aProtein.getOriginalAccession() + " to " + aProtein.getProteinAccession(),false));
                }
            } catch (ConversionException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
        return Collections.unmodifiableList(proteinList);
    }

    @Override
    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        return new AccessionConverting(aProteinList);
    }

    public enum ConversionTo {

        TO_UNIPROT;

    }

    public void setConversionTo(ConversionTo convertTo) {
        this.coTo = convertTo;
    }

    @Override
    public String getRetrievalStepDescription() {
        return "Convert Accessions";
    }
}
