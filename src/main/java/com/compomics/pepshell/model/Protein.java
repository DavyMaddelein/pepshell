package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy
 */
public class Protein {

    private ProteinInfo proteinInfo = new ProteinInfo();
    private String accession;
    private int projectId;
    private String sequence = "";
    private List<Domain> domainsFoundInProtein = new ArrayList<Domain>();
    private List<PeptideGroup> peptideGroupsForProtein = new ArrayList<PeptideGroup>();
    private Set<PdbInfo> allPDBFileInfoForProtein = new HashSet<PdbInfo>();
    private String proteinName;
    private String originalAccession;
    private String visibleAccession;

    public Protein(String accession) {
        this.accession = accession;
        this.visibleAccession = accession;
        this.originalAccession = accession;
    }

    public Protein(String accession, String sequence) {
        this(accession);
        this.sequence = sequence;
    }

    public String getProteinSequence() {
        return this.sequence;
    }

    public String getProteinAccession() {
        return this.accession;
    }

    public String getProteinName() {
        return proteinName;
    }

    public void setProteinName(String aProteinName) {
        this.proteinName = aProteinName;
    }

    public List<Domain> getDomains() {
        return domainsFoundInProtein;
    }

    public void addDomains(List<Domain> domainsToAdd) {
        this.domainsFoundInProtein.addAll(domainsToAdd);
    }

    public List<PeptideGroup> getPeptideGroupsForProtein() {
        return Collections.unmodifiableList(peptideGroupsForProtein);
    }

    public void setPeptideGroupsForProtein(List<PeptideGroup> listOfPeptides) {
        peptideGroupsForProtein.addAll(listOfPeptides);
    }

    public void addPeptideGroup(PeptideGroup aPeptideGroup) {
        peptideGroupsForProtein.add(aPeptideGroup);
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectid() {
        return this.projectId;
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
        this.allPDBFileInfoForProtein.addAll(allPDBFileNamesForProtein);
    }

    public Set<PdbInfo> getPdbFilesInfo() {
        return allPDBFileInfoForProtein;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.accession != null ? this.accession.hashCode() : 0);
        hash = 59 * hash + (this.visibleAccession != null ? this.visibleAccession.hashCode() : 0);
        hash = 59 * hash + (this.sequence != null ? this.sequence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean returnValue = false;
        if (obj != null) {
            if (getClass() == obj.getClass()) {
                final Protein other = (Protein) obj;
                if ((this.visibleAccession == null) ? (other.getVisibleAccession() != null) : this.visibleAccession.equals(other.getVisibleAccession())
                        || (this.originalAccession == null) ? (other.getProteinAccession() != null) : this.originalAccession.equals(other.getProteinAccession())) {
                    returnValue = true;
                }
                if (this.sequence == null && other.getProteinSequence() != null) {
                    if (!other.getProteinSequence().isEmpty() && this.sequence.equals(other.getProteinSequence())) {
                        returnValue = true;
                    }
                }
            }
        }
        return returnValue;
    }

    public void setAccession(String anAccession) {
        this.accession = anAccession;
    }

    public String getOriginalAccession() {
        return this.originalAccession;
    }

    public void addPeptideGroups(List<PeptideGroup> aListOfPeptideGroups) {
        for (PeptideGroup aGroup : aListOfPeptideGroups) {
            if (!peptideGroupsForProtein.contains(aGroup)) {
                peptideGroupsForProtein.add(aGroup);
            } else {
                peptideGroupsForProtein.get(peptideGroupsForProtein.indexOf(aGroup)).addPeptides(aGroup.getPeptideList());
            }
        }
    }

    public String getVisibleAccession() {
        return visibleAccession;
    }

    public void setVisibleAccession(String visibleAccession) {
        this.visibleAccession = visibleAccession;
    }
}
