package com.compomics.pepshell.controllers.dataimport.filevalidation;

import com.compomics.pepshell.model.AnnotatedFile;
import com.compomics.pepshell.model.HDXMetaDataAnnotations;

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
    public boolean canValidateFile(File aFile) {
        return aFile.getName().contains(".hdx") || (aFile instanceof AnnotatedFile && canValidateFile((AnnotatedFile)aFile));
    }

    @Override
    public boolean canValidateFile(AnnotatedFile annotatedFile) {
       return false;
    }


    @Override
    public boolean validateFile(AnnotatedFile annotatedFile) {
        return false;
    }
}
