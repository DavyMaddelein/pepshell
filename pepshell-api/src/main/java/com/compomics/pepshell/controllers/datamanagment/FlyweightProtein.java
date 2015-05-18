/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.controllers.datamanagment;

import com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import com.compomics.pepshell.model.protein.proteininfo.ProteinInfo;

import java.util.List;
import java.util.Set;


/**
 * a bare essentials protein object to use when protein objects are heavily proliferated in code
 * Created by Davy Maddelein on 17/02/2015.
 */

public class FlyweightProtein extends PepshellProtein {

    /**
     * a flyweight protein defined by a given accession
     *
     * @param anAccession the identifying accession of the protein, this will set the original and the visible accession to the passed accession
     */
    public FlyweightProtein(String anAccession) {
        super(anAccession);
    }


    public FlyweightProtein(PepshellProtein aProtein){
        super(aProtein.getOriginalAccession(), aProtein.getVisibleAccession());
        if (!ProteinStoreManager.getInstance().storeContains(aProtein.getOriginalAccession())){
            ProteinStoreManager.getInstance().addToStore(aProtein);
        }
    }


    /**
     * a flyweight protein defined by a given accession and an extra identifier
     *
     * @param anAccession            the identifying accession of this protein, this will set the original and the visible accession to the passed accession
     * @param anAdditionalIdentifier an extra identifier which can be used for non accession based lookups (such as database, key value, internet, ...)
     */
    public FlyweightProtein(String anAccession, String anAdditionalIdentifier) {
        super(anAccession, anAdditionalIdentifier);
    }

    //part that the flyweight should contain

    /**
     * {@inheritDoc}
     */
    @Override
    public String getExtraIdentifier() {
        return extraIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setExtraIdentifier(String anExtraIdentifier) {
        this.extraIdentifier = anExtraIdentifier;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getOriginalAccession() {
        return originalAccession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getVisibleAccession() {
        return visibleAccession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setOriginalAccession(String anAccession) {
        this.originalAccession = anAccession;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setVisibleAccession(String visibleAccession) {
        this.visibleAccession = visibleAccession;
    }


    //additional data that needs to be retrieved from the store

    /**
     * gets the protein sequence from the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @return the protein sequence
     */
    @Override
    public String getProteinSequence() {
        return ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).getProteinSequence();

    }

    /**
     * gets the protein name from the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @return the protein name
     */
    @Override
    public String getProteinName() {
        return ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).getProteinSequence();
    }

    /**
     * sets the protein name for the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @param aProteinName the name to set
     */
    @Override
    public void setProteinName(String aProteinName) {

        ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).setProteinName(aProteinName);
    }

    /**
     * gets the domains from the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @return the domains added to the protein
     */
    @Override
    public List<FeatureWithLocation> getDomains() {
        return ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).getDomains();
    }

    /**
     * adds domain information to the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @param domainsToAdd the domains to add to the protein
     */
    @Override
    public void addDomains(List<FeatureWithLocation> domainsToAdd) {
        ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).addDomains(domainsToAdd);
    }

    /**
     * sets the protein info for the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @param proteinInfo the info to set
     */
    @Override
    public void setProteinInfo(ProteinInfo proteinInfo) {
        ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).setProteinInfo(proteinInfo);
    }

    /**
     * gets the protein info from the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @return the protein info
     */
    @Override
    public ProteinInfo getProteinInfo() {
        return ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).getProteinInfo();
    }

    /**
     * sets the protein sequence for the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @param sequence the sequence to set
     */
    @Override
    public void setSequence(String sequence) {
        ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).setSequence(sequence);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * add PDB file info to the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @param allPDBFileNamesForProtein the pdb info to set
     */
    @Override
    public void addPdbFileInfo(Set<PdbInfo> allPDBFileNamesForProtein) {
        ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).addPdbFileInfo(allPDBFileNamesForProtein);
    }

    /**
     * gets the pdb info from the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @return the pdb info
     */
    @Override
    public Set<PdbInfo> getPdbFilesInfo() {
        return super.getPdbFilesInfo();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    /**
     * adds peptide groups to this protein
     *
     * @param aListOfPeptideGroups the peptide groups to add to this protein
     * @return this protein
     */
    @Override
    public PepshellProtein addPeptideGroups(List<? extends PeptideGroup> aListOfPeptideGroups) {
        return super.addPeptideGroups(aListOfPeptideGroups);
    }

    /**
     * sets the CPDT cleavage results for the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @param CPDTPeptides the cleavage results to set
     */
    @Override
    public void setCPDTPeptideList(List<PeptideGroup> CPDTPeptides) {
        ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).setCPDTPeptideList(CPDTPeptides);
    }

    /**
     * adds CPDT cleavage results to the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @param CPDTPeptides the
     */
    @Override
    public void addCPDTPeptides(List<PeptideGroup> CPDTPeptides) {
        ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).addCPDTPeptides(CPDTPeptides);
    }

    /**
     * returns the CPDT cleavage results from the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @return the CPDT cleavage results
     */
    @Override
    public List<PeptideGroup> getCPDTPeptideGroups() {
        return ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).getCPDTPeptideGroups();
    }

    /**
     * gets the preferred pdb file for the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @return the preferred pdb file
     */
    @Override
    public PdbInfo getPreferredPdbFile() {
        return ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).getPreferredPdbFile();
    }

    /**
     * returns the CPDT cleavage results for the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @return the CPDT cleavage results
     */
    @Override
    public List<PeptideGroup> getCPDTPeptideList() {
        return super.getCPDTPeptideList();
    }

    /**
     * sets the preffered pdb file for the protein returned by the {@link com.compomics.pepshell.controllers.datamanagment.cachesandstores.ProteinStoreManager}
     *
     * @param preferredPdbFile the preferred pdb file
     */
    @Override
    public void setPreferredPdfFile(PdbInfo preferredPdbFile) {
        ProteinStoreManager.getInstance().retrieveFromStore(this.getOriginalAccession()).setPreferredPdfFile(preferredPdbFile);
    }
}
