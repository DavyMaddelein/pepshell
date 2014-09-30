package com.compomics.pepshell.model.enums;

import java.util.EnumSet;

/**
 *
 * @author Davy
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
    //TODO: make interface method reference implementing class?
    public static EnumSet allEnumValues = EnumSet.allOf(ProgramPropertyEnum.class);

}
