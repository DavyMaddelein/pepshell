package com.compomics.pepshell.controllers.DataSources;

import com.compomics.pepshell.model.Domain;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import java.util.List;

/**
 *
 * @author Davy
 * @param <T>
 */
public interface StructureDataSource<T extends Protein<PeptideGroup<Peptide>>> extends AbstractDataSource {

    public List<Domain> getDomainData(T aProtein);

    public String getPDBDataForPDBName(String pdbAccession);

    //TODO think of a cleaner way to handle below
    public boolean isAbleToGetFreeEnergy();

    public double getFreeEnergyForResidue(T protein, int location);

    public boolean isAbleToGetSolventAccessability();

    public double getRelativeSolventAccessabilityForResidue(T protein, int location);

    public void getSecondaryStructureForResidue(T protein, int location);
}
