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

package com.compomics.pepshell.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy Maddelein
 */
public enum ProgramPropertyEnum implements PropertyEnum {

    ACCESSION_MASKING("pepshell.dataretrievalstep.accession_masking", "com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AccessionMasking"),
    ADD_DOMAINS("pepshell.dataretrievalstep.add_domains", "com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AddDomains"),
    ADD_PDB_INFO("pepshell.dataretrievalstep.add_pdb_info", "com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AddPdbInfo"),
    ACCESSION_CONVERTING("pepshell.dataretrievalstep.accession_converting", "com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AccessionConverting"),
    PROTEIN_FILTERING("pepshell.dataretrievalstep.protein_filtering", "com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.ProteinFiltering"),
    CPDT_ANALYSIS("pepshell.dataretrievalstep.CPDTAnalysis", "com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.CPDTAnalysis");
    private final String keyName;
    private final String defaultValue;

    private ProgramPropertyEnum(String keyName, String defaultValue) {
        this.keyName = keyName;
        this.defaultValue = defaultValue;
    }

    @Override
    public String getKey() {
        return this.keyName;
    }

    @Override
    public String getDefaultValue() {
        return this.defaultValue;
    }

}
