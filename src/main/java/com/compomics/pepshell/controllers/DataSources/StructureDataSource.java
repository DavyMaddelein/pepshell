package com.compomics.pepshell.controllers.DataSources;

import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.InteractionPartner;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 * @param <T>
 */
public interface StructureDataSource extends AbstractDataSource {

    public StructureDataSource getInstance();

    public List<Domain> getDomainData(Protein aProtein) throws DataRetrievalException;

    public String getPDBDataForPDBName(String pdbAccession);

    public List<InteractionPartner> getInteractionPartners(Protein aProtein);

    public List<InteractionPartner> getInteractionPartnersForRange(Protein aProtein, int start, int stop);

    //TODO think of a cleaner way to handle below
    public boolean isAbleToGetFreeEnergy();

    public double getFreeEnergyForResidue(Protein protein, int location);

    public boolean isAbleToGetSolventAccessibility();

    public double getRelativeSolventAccessibilityForResidue(Protein protein, int location);

    public void getSecondaryStructureForResidue(Protein protein, int location);

    public List<InteractionPartner> getInteractionPartnersForPDBName(String string);

    public Set<PdbInfo> getPdbInforForProtein(Protein protein, Comparator<PdbInfo> sortingComparator);

}
