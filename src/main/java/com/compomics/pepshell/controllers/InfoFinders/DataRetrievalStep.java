package com.compomics.pepshell.controllers.InfoFinders;

import com.compomics.pepshell.model.Protein;
import java.util.List;

/**
 *
 * @author Davy
 */
public interface DataRetrievalStep {

    public List<Protein> execute(List<Protein> proteinList);
    
    public boolean isMultithreadAble();

}
