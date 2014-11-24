package com.compomics.pepshell.controllers.fileparservalidators;

import com.compomics.pepshell.model.SeparatedvalueExperimentMetaData;
import com.compomics.pepshell.model.exceptions.CannotValidateException;

import java.io.File;

/**
 * Created by Davy Maddelein on 20/11/2014.
 */
public interface FileValidatorInterface {

    /**
     * checks if a file is a valid input for parsing
     * @param aFile The file to validate
     * @return a boolean, true if validated, false otherwise
     * @throws com.compomics.pepshell.model.exceptions.CannotValidateException if the validation cannot be executed for any reason
     */
    public boolean validateFile(File aFile) throws CannotValidateException;

    /**
     * checks if a file is a valid input given the extra annotations on how to parse the file
     * @param aFile The file to validate
     * @param metaData The added parsing hints to take into account when validating
     * @return a boolean, true if validated, false otherwise
     * @throws com.compomics.pepshell.model.exceptions.CannotValidateException if the validation cannot be executed for any reason
     */
    public boolean validateFile(File aFile,SeparatedvalueExperimentMetaData metaData) throws CannotValidateException;

}
