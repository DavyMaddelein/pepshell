package com.compomics.peppi.controllers.DataModes;

import com.compomics.peppi.controllers.ViewPreparation.ViewPreparationForFastaData;
import java.io.File;

/**
 *
 * @author Davy
 */
public class FastaDataMode extends AbstractDataMode {

    private File fastaFile;

    public FastaDataMode() {
        super(new ViewPreparationForFastaData());
    }

    public FastaDataMode(File aFastaFile) {
        super(new ViewPreparationForFastaData());
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
