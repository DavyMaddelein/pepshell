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
package com.compomics.pepshell.model.protein.proteinimplementations;

import com.compomics.pepshell.controllers.comparators.ComparePdbInfoByResolution;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.protein.ProteinInterface;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;
import com.compomics.pepshell.model.protein.proteininfo.ProteinFeatureWithLocation;
import com.compomics.pepshell.model.protein.proteininfo.ProteinInfo;

import java.util.*;

/**
 * protein class which can store all needed data for Pepshell
 * @author Davy Maddelein
 */
public class PepshellProtein implements ProteinInterface {

    private ProteinInfo proteinInfo = new ProteinInfo();
    protected String extraIdentifier;
    private String sequence = "";
    private final List<ProteinFeatureWithLocation> domainsFoundInProtein = new ArrayList<>();
    private final List<PeptideGroup> peptideGroupsForProtein = new ArrayList<>();
    private final Set<PdbInfo> allPDBFileInfoForProtein = new TreeSet<>(new ComparePdbInfoByResolution());
    private String proteinName;
    protected String originalAccession;
    protected String visibleAccession;
    private List<PeptideGroup> CPDTCleavageList = new ArrayList<>();
    private PdbInfo preferedPdbFile;

    public PepshellProtein(String accession) {
        this.visibleAccession = accession;
        this.originalAccession = accession;
    }

    public PepshellProtein(String accession, String sequence) {
        this(accession);
        this.sequence = sequence;
    }

    public String getProteinSequence() {
        return this.sequence;
    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String aProteinName) {
        this.proteinName = aProteinName;
    }

    public List<ProteinFeatureWithLocation> getDomains() {
        return Collections.unmodifiableList(domainsFoundInProtein);
    }

    public void addDomains(List<ProteinFeatureWithLocation> domainsToAdd) {
        this.domainsFoundInProtein.addAll(domainsToAdd);
    }

    public List<PeptideGroup> getPeptideGroups() {
        return Collections.unmodifiableList(peptideGroupsForProtein);
    }

    public void setPeptideGroupsForProtein(List<? extends PeptideGroup> listOfPeptides) {
        peptideGroupsForProtein.addAll(listOfPeptides);
    }

    @Override
    public void setExtraIdentifier(String anExtraIdentifier) {
        this.extraIdentifier = anExtraIdentifier;
    }

    public PepshellProtein addPeptideGroup(PeptideGroup aPeptideGroup) {
        peptideGroupsForProtein.add(aPeptideGroup);
        return this;
    }

    @Override
    public String getExtraIdentifier() {
        return this.extraIdentifier;
    }

    public void setProteinInfo(ProteinInfo proteinInfo) {
        this.proteinInfo = proteinInfo;
    }

    public ProteinInfo getProteinInfo() {
        return this.proteinInfo;
    }

    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return visibleAccession;
    }

    public void addPdbFileInfo(Set<PdbInfo> allPDBFileNamesForProtein) {
        this.allPDBFileInfoForProtein.clear();
        this.allPDBFileInfoForProtein.addAll(allPDBFileNamesForProtein);
    }

    public Set<PdbInfo> getPdbFilesInfo() {
        return Collections.unmodifiableSet(allPDBFileInfoForProtein);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.originalAccession != null ? this.originalAccession.hashCode() : 0);
        hash = 59 * hash + (this.visibleAccession != null ? this.visibleAccession.hashCode() : 0);
        hash = 59 * hash + (this.sequence != null ? this.sequence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean returnValue = false;
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                final PepshellProtein other = (PepshellProtein) obj;

                if (this.visibleAccession != null && other.getVisibleAccession() != null) {
                    if (this.visibleAccession.equals(other.getVisibleAccession())) {
                        returnValue = true;
                    }
                }
                if (this.originalAccession != null && other.getOriginalAccession() != null) {
                    returnValue = this.originalAccession.equals(other.getOriginalAccession());
                }
                if (this.sequence != null && other.getProteinSequence() != null && !other.getProteinSequence().isEmpty() && !this.sequence.isEmpty()) {
                    if (this.sequence.equals(other.getProteinSequence())) {
                        returnValue = true;
                    }

                }
            }
        }
        return returnValue;
    }

    @Override
    public void setOriginalAccession(String anAccession) {
        this.originalAccession = anAccession;
    }

    @Override
    public String getOriginalAccession() {
        return this.originalAccession;
    }

    public PepshellProtein addPeptideGroups(List<? extends PeptideGroup> aListOfPeptideGroups) {
        aListOfPeptideGroups.stream().forEach((aGroup) -> {
            if (!peptideGroupsForProtein.contains(aGroup)) {
                peptideGroupsForProtein.add(aGroup);
            } else {
                peptideGroupsForProtein.get(peptideGroupsForProtein.indexOf(aGroup)).addPeptides(aGroup.getPeptideList());
            }
        });
        return this;
    }

    @Override
    public String getVisibleAccession() {
        return visibleAccession;
    }

    @Override
    public void setVisibleAccession(String visibleAccession) {
        this.visibleAccession = visibleAccession;
    }

    public void setCPDTPeptideList(List<PeptideGroup> CPDTList) {
        this.CPDTCleavageList.clear();
        this.CPDTCleavageList.addAll(CPDTList);
    }

    public void addCPDTPeptides(List<PeptideGroup> parseCPDTOutput) {
        this.CPDTCleavageList.addAll(parseCPDTOutput);
    }

    public List<PeptideGroup> getCPDTPeptideGroups() {
        return Collections.unmodifiableList(this.CPDTCleavageList);
    }

    public PdbInfo getPreferredPdbFile() {
        return preferedPdbFile;
    }

    public void setPreferredPdfFile(PdbInfo preferedPdbFile) {
        this.preferedPdbFile = preferedPdbFile;
    }

    public List<PeptideGroup> getCPDTPeptideList() {
        return CPDTCleavageList;
    }
}
