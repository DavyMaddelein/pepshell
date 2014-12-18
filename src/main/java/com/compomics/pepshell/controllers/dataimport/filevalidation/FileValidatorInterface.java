package com.compomics.pepshell.controllers.dataimport.filevalidation;

import com.compomics.pepshell.model.AnnotatedFile;
import com.compomics.pepshell.model.SeparatedvalueExperimentMetaData;
import com.compomics.pepshell.model.exceptions.CannotValidateException;
import com.compomics.pepshell.model.exceptions.ExperimentMetaData;

import java.io.File;

/**
 * Created by Davy Maddelein on 20/11/2014.
 */
public interface  FileValidatorInterface<T extends ExperimentMetaData> {

    /**
     * checks if a file is a valid input for parsing
     * @param aFile The file to validate
     * @return a boolean, true if validated, false otherwise
     * @throws com.compomics.pepshell.model.exceptions.CannotValidateException if the validation cannot be executed for any reason
     */
    public boolean validateFile(File aFile) throws CannotValidateException;

    /**
     * checks if a file is a valid input given the extra annotations on how to parse the file
     * @param annotatedFile The file to validate
     * @return a boolean, true if validated, false otherwise
     * @throws com.compomics.pepshell.model.exceptions.CannotValidateException if the validation cannot be executed for any reason
     */

    public boolean validateFile(AnnotatedFile<T> annotatedFile) throws CannotValidateException;

    public boolean canValidateFile(File aFile);

    public boolean canValidateFile(AnnotatedFile<T> annotatedFile);

}
