package com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps;

import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.UpdateMessage;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class AccessionMasking extends DataRetrievalStep {

    private Set<Protein> maskingSet = new HashSet<>();

    private AccessionMasking(List<Protein> aProteinList) {
        this.proteinList = aProteinList;
    }

    public AccessionMasking() {
    }

    @Override
    public DataRetrievalStep getInstance(List<Protein> aProteinList) {
        AccessionMasking toReturn = new AccessionMasking(aProteinList);
        toReturn.setMaskingSet(maskingSet);
        return toReturn;
    }

    @Override
    public List<Protein> call() throws Exception {
        int counter = 0;
        for (Protein maskingProtein : maskingSet) {
            if (proteinList.contains(maskingProtein)) {
                Protein matchedProtein = proteinList.get(proteinList.indexOf(maskingProtein));
                matchedProtein.setVisibleAccession(maskingProtein.getVisibleAccession());
                counter++;
                this.setChanged();
                this.notifyObservers(new UpdateMessage(true,"masked accession of " + matchedProtein.getOriginalAccession() + " with " + matchedProtein.getProteinAccession()));
            }
        }
        return Collections.unmodifiableList(proteinList);
    }

    public void setMaskingSet(Set<Protein> aMaskingSet) {
        maskingSet.clear();
        this.maskingSet.addAll(aMaskingSet);
    }

    @Override
    public String toString() {
        return "Accession Masking";
    }
}
