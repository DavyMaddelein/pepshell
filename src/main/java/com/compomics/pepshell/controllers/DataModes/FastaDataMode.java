package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.ExternalStructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.ViewPreparationForFastaData;
import java.io.File;

/**
 *
 * @author Davy
 */
public class FastaDataMode extends AbstractDataMode {

    private File fastaFile;

    public FastaDataMode() {
        super(new ViewPreparationForFastaData(),new ExternalStructureDataSource());
    }

    public FastaDataMode(File aFastaFile) {
        super(new ViewPreparationForFastaData(), new ExternalStructureDataSource());
        this.fastaFile = aFastaFile;
    }

    public File getFastaFile() {
        return fastaFile;
    }

    public void setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
        ((ViewPreparationForFastaData) this.getViewPreparationForMode()).setFastaFile(aFastaFile);
    }
}
