package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Davy
 */
public class AccessionConverting implements DataRetrievalStep {

    public enum ConversionTo {

        TO_UNIPROT;

    }

    private ConversionTo coTo = ConversionTo.TO_UNIPROT;

    public List<Protein> execute(List<Protein> proteinList) {
        for (Protein aProtein : proteinList) {
            try {
                try {
                    if (coTo == ConversionTo.TO_UNIPROT) {
                        aProtein.setAccession(AccessionConverter.toUniprot(aProtein.getOriginalAccession()));
                    }
                } catch (IOException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
            } catch (ConversionException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
        return proteinList;
    }

    public void setConversionTo(ConversionTo convertTo) {
        this.coTo = convertTo;
    }

    public boolean isMultithreadAble() {
        return true;
    }
}
