package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.ExternalStructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.DataRetrievalForFasta;
import java.io.File;

/**
 *
 * @author Davy
 */
public class FastaDataMode extends AbstractDataMode {

    private File fastaFile;

    public FastaDataMode() {
        super(new DataRetrievalForFasta(),new ExternalStructureDataSource());
    }

    public FastaDataMode(File aFastaFile) {
        super(new DataRetrievalForFasta(), new ExternalStructureDataSource());
        this.fastaFile = aFastaFile;
    }

    public File getFastaFile() {
        return fastaFile;
    }

    public void setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
        ((DataRetrievalForFasta) this.getViewPreparationForMode()).setFastaFile(aFastaFile);
    }
}
