package com.compomics.pepshell.controllers.fileparservalidators;

import com.compomics.pepshell.model.SeparatedvalueExperimentMetaData;
import com.compomics.pepshell.model.exceptions.CannotValidateException;

import java.io.File;

/**
 * Created by Davy Maddelein on 20/11/2014.
 */
public class FileValidatorFactory {
    private static FileValidatorFactory instance = new FileValidatorFactory();
    private File fileToValidate;
    private SeparatedvalueExperimentMetaData metaData;

    public static FileValidatorFactory getInstance() {
        return instance;
    }

    private FileValidatorFactory() {
    }

    public void addFileToValidate(File aFile){
        fileToValidate = aFile;

    }

    public void addMetaDataForValidation(SeparatedvalueExperimentMetaData additionalInformation){
        metaData = additionalInformation;

    }

    public boolean validate() throws CannotValidateException {
        boolean validated = false;
        if (fileToValidate == null || !fileToValidate.exists()){
                throw new CannotValidateException("no file was given or the file could not be found");
        }
        if (fileToValidate.getName().contains(".hdx")){
            if (metaData != null){

                validated = new HDXFileValidator().validateFile(fileToValidate);
            } else {
                validated = new HDXFileValidator().validateFile(fileToValidate, metaData);
            }

        } else if (metaData != null){
            validated = new AbstractFileValidator().validateFile(fileToValidate, metaData);

        }

        return validated;
    }
}
