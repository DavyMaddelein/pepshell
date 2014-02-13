package com.compomics.pepshell.controllers.InfoFinders;

import com.compomics.pepshell.model.Protein;
import java.util.List;

/**
 *
 * @author Davy
 */
public interface DataRetievalStep {
    
    public void execute(List<Protein> proteinList);
    
}
