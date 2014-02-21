package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.ProgressMessage;
import com.compomics.pepshell.model.Protein;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class AccessionMasking implements DataRetrievalStep {

    private final Observable notifier = new Observable();

    private Set<Protein> maskingSet = new HashSet<Protein>();

    private List<Protein> proteinList = new ArrayList<Protein>();

    private AccessionMasking(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public AccessionMasking() {
    }

    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        AccessionMasking toReturn = new AccessionMasking(aProteinList);
        toReturn.setMaskingSet(maskingSet);
        return toReturn;
    }

    public List<Protein> call() throws Exception {
        int counter = 0;
        for (Protein maskingProtein : maskingSet) {
            if (proteinList.contains(maskingProtein)) {
                proteinList.get(proteinList.indexOf(maskingProtein)).setVisibleAccession(maskingProtein.getVisibleAccession());
                counter++;
                this.getNotifier().hasChanged();
                this.getNotifier().notifyObservers(new ProgressMessage("masking accessions", (int) Math.floor(counter / maskingSet.size())));
            }
        }
        return Collections.unmodifiableList(proteinList);
    }

    public void setMaskingSet(Set<Protein> aMaskingSet) {
        maskingSet.clear();
        this.maskingSet.addAll(aMaskingSet);
    }

    @Override
    public Observable getNotifier() {
        return this.notifier;
    }
    
    @Override
    public String toString(){
        return "Accession Masking";
    }
}
