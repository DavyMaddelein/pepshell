package com.compomics.pepshell.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy Maddelein
 */
public class Protein implements ProteinInterface {

    private ProteinInfo proteinInfo = new ProteinInfo();
    private String accession;
    private int projectId;
    private String sequence = "";
    private final List<Domain> domainsFoundInProtein = new ArrayList<>();
    private final List<PeptideGroup> peptideGroupsForProtein = new ArrayList<>();
    private final Set<PdbInfo> allPDBFileInfoForProtein = new HashSet<>();
    private String proteinName;
    private String originalAccession;
    private String visibleAccession;
    private List<PeptideGroup> CPDTCleavageList = new ArrayList<>();
    private PdbInfo preferedPdbFile;

    public Protein(String accession) {
        this.accession = accession;
        this.visibleAccession = accession;
        this.originalAccession = accession;
    }

    public Protein(String accession, String sequence) {
        this(accession);
        this.sequence = sequence;
    }

    @Override
    public String getProteinSequence() {
        return this.sequence;
    }

    @Override
    public String getProteinAccession() {
        return this.accession;
    }

    @Override
    public String getProteinName() {
        return proteinName;
    }

    @Override
    public void setProteinName(String aProteinName) {
        this.proteinName = aProteinName;
    }

    @Override
    public List<Domain> getDomains() {
        return Collections.unmodifiableList(domainsFoundInProtein);
    }

    @Override
    public void addDomains(List<Domain> domainsToAdd) {
        this.domainsFoundInProtein.addAll(domainsToAdd);
    }

    @Override
    public List<PeptideGroup> getPeptideGroupsForProtein() {
        return Collections.unmodifiableList(peptideGroupsForProtein);
    }

    
    
    @Override
    public <T extends PeptideGroup> void setPeptideGroupsForProtein(List<T> listOfPeptides) {
        peptideGroupsForProtein.addAll(listOfPeptides);
    }

    @Override
    public Protein addPeptideGroup(PeptideGroup aPeptideGroup) {
        peptideGroupsForProtein.add(aPeptideGroup);
        return this;
    }

    @Override
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    @Override
    public int getProjectid() {
        return this.projectId;
    }

    @Override
    public void setProteinInfo(ProteinInfo proteinInfo) {
        this.proteinInfo = proteinInfo;
    }

    @Override
    public ProteinInfo getProteinInfo() {
        return this.proteinInfo;
    }

    @Override
    public void setSequence(String sequence) {
        this.sequence = sequence;
    }

    @Override
    public String toString() {
        return visibleAccession;
    }

    @Override
    public void addPdbFileInfo(Set<PdbInfo> allPDBFileNamesForProtein) {
        this.allPDBFileInfoForProtein.clear();
        this.allPDBFileInfoForProtein.addAll(allPDBFileNamesForProtein);
    }

    @Override
    public Set<PdbInfo> getPdbFilesInfo() {
        return Collections.unmodifiableSet(allPDBFileInfoForProtein);
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
                    if (!other.getProteinSequence().isEmpty() && (this.sequence != null && this.sequence.equals(other.getProteinSequence()))) {
                        returnValue = true;
                    }
                }
            }
        }
        return returnValue;
    }

    @Override
    public void setAccession(String anAccession) {
        this.accession = anAccession;
    }

    @Override
    public String getOriginalAccession() {
        return this.originalAccession;
    }

    @Override
    public Protein addPeptideGroups(List<PeptideGroup> aListOfPeptideGroups) {
        for (PeptideGroup aGroup : aListOfPeptideGroups) {
            if (!peptideGroupsForProtein.contains(aGroup)) {
                peptideGroupsForProtein.add(aGroup);
            } else {
                peptideGroupsForProtein.get(peptideGroupsForProtein.indexOf(aGroup)).addPeptides(aGroup.getPeptideList());
            }
        }
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

    @Override
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

    public PdbInfo getPreferedPdbFile() {
        return preferedPdbFile;
    }

    public void setPreferedPdbFile(PdbInfo preferedPdbFile) {
        this.preferedPdbFile = preferedPdbFile;
    }    

    @Override
    public List<PeptideGroup> getCPDTPeptideList() {
        return CPDTCleavageList;
    }
}
