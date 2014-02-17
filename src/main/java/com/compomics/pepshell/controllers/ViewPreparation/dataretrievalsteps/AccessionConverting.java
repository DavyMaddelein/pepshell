package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.ConversionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 *
 * @author Davy
 */
public class AccessionConverting implements DataRetrievalStep {

    private Observable notifier = new Observable();

    private List<Protein> proteinList = new ArrayList<Protein>();

    private ConversionTo coTo = ConversionTo.TO_UNIPROT;

    public AccessionConverting(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public AccessionConverting() {
    }

    public List<Protein> call() throws Exception {
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

    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        return new AccessionConverting(aProteinList);
    }

    public Observable getNotifier() {
        return this.notifier;
    }

    public enum ConversionTo {

        TO_UNIPROT;

    }

    public void setConversionTo(ConversionTo convertTo) {
        this.coTo = convertTo;
    }
}
