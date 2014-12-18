package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.ExternalStructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.DataRetrievalForFasta;
import java.io.File;

/**
 *
 * @author Davy Maddelein
 */
public class FileDataMode extends AbstractDataMode {

    private File fastaFile;

    public FileDataMode() {
        super(new DataRetrievalForFasta(),new ExternalStructureDataSource(),null);
    }

    public FileDataMode(File aFastaFile) {
        super(new DataRetrievalForFasta(), new ExternalStructureDataSource(),null);
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
