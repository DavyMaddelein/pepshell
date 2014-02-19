package com.compomics.pepshell.model.exceptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy
 */
public class AggregateFastaReadingException extends IOException {

    public AggregateFastaReadingException(){
        super();
    }
    
    public AggregateFastaReadingException(String message){
        super(message);
    }
    
    private List<FastaCouldNotBeReadException> exceptionList = new ArrayList<>();
    
    public void addFastaReadingException(FastaCouldNotBeReadException ex) {
        exceptionList.add(ex);
    }

    public List<FastaCouldNotBeReadException> getExceptionList() {
        return Collections.unmodifiableList(exceptionList);
    }
    
}
