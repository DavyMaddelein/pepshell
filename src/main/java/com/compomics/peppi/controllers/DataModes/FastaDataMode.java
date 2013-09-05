package com.compomics.peppi.controllers.DataModes;

import com.compomics.peppi.controllers.ViewPreparation.PreparationForOfflineData;
import java.io.File;

/**
 *
 * @author Davy
 */
public class FastaDataMode extends AbstractDataMode {

    private File fastaFile;

    public FastaDataMode(File aFastaFile) {
        super(new PreparationForOfflineData());
        this.fastaFile = aFastaFile;
    }

    public File getFastaFile() {
        return fastaFile;
    }
}
