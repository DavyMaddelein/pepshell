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

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.ProteinInterface;
import com.compomics.pepshell.model.UpdateMessage;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Davy Maddelein
 */
public class AddPdbInfo extends DataRetrievalStep {

    private Consumer<PepshellProtein> setNotification = new SetNotification();
    private final Consumer<PepshellProtein> addInfoToProtein = new AddInfoToProtein();

    private AddPdbInfo(List<PepshellProtein> aPepshellProteinList) {
        this.pepshellProteinList = aPepshellProteinList;
    }

    public AddPdbInfo() {
    }

    @Override
    public DataRetrievalStep getInstance(List<PepshellProtein> aPepshellProteinList) {
        return new AddPdbInfo(aPepshellProteinList);
    }

    @Override
    public List<PepshellProtein> call() throws Exception {

        pepshellProteinList.stream().forEach(e -> addInfoToProtein.andThen(setNotification));
        return Collections.unmodifiableList(pepshellProteinList);
    }

    private class SetNotification implements Consumer<PepshellProtein> {

        /**
         * Performs this operation on the given argument.
         *
         * @param protein the input argument
         */
        @Override
        public void accept(PepshellProtein protein) {
            setChanged();
            notifyObservers(new UpdateMessage(false, "added PDB info to " + protein.getVisibleAccession(), false));
        }
    }

    private class AddInfoToProtein implements Consumer<PepshellProtein> {

        /**
         * Performs this operation on the given argument.
         *
         * @param protein the input argument
         */
        @Override
        public void accept(PepshellProtein protein) {
            protein.addPdbFileInfo(ProgramVariables.STRUCTUREDATASOURCE.getPdbInforForProtein(protein));
        }
    }

    @Override
    public String getRetrievalStepDescription() {
        return "Add PDB info";
    }
}
