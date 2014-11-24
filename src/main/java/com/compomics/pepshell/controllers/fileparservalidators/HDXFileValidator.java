package com.compomics.pepshell.controllers.fileparservalidators;

import com.compomics.pepshell.model.SeparatedvalueExperimentMetaData;

import java.io.File;

/**
 * Created by Davy Maddelein on 20/11/2014.
 */
class HDXFileValidator implements FileValidatorInterface {


    @Override
    public boolean validateFile(File aFile) {
        return false;
    }

    @Override
    public boolean validateFile(File aFile, SeparatedvalueExperimentMetaData metaData) {
        return false;
    }
}
