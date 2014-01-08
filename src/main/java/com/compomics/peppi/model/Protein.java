package com.compomics.peppi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy
 */
public class Protein extends ArrayList<PeptideGroup> {

    private ProteinInfo proteinInfo = new ProteinInfo();
    private final String accession;
    private int projectId;
    private String sequence = "";
    private List<Domain> domainsFoundInProtein = new ArrayList<Domain>();
    private List<String> allPDBFileNamesForProtein = new ArrayList<String>();

    public Protein(String accession) {
        this.accession = accession;
    }

    public Protein(String accession, String sequence) {
        this.accession = accession;
        this.sequence = sequence;
    }

    public String getProteinSequence() {
        return this.sequence;
    }

    public String getProteinAccession() {
        return this.accession;
    }

    public List<Domain> getDomains() {
        return domainsFoundInProtein;
    }

    public void addDomains(List<Domain> domainsToAdd) {
        this.domainsFoundInProtein.addAll(domainsToAdd);
    }

    public List<PeptideGroup> getPeptideGroupsForProtein() {
        return Collections.unmodifiableList(this);
    }

    public void setPeptideGroupsForProtein(List<PeptideGroup> listOfPeptides) {
        this.addAll(listOfPeptides);
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
        return accession;
    }

    public void addPdbFileNames(List<String> allPDBFileNamesForProtein) {
        this.allPDBFileNamesForProtein.addAll(allPDBFileNamesForProtein);
    }

    public List<String> getPdbFileNames() {
        return allPDBFileNamesForProtein;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.accession != null ? this.accession.hashCode() : 0);
        hash = 59 * hash + (this.sequence != null ? this.sequence.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        boolean returnValue = true;
        if (obj == null) {
            returnValue = false;
        } else {
            if (getClass() != obj.getClass()) {
                returnValue = false;
            } else {
                final Protein other = (Protein) obj;
                if ((this.accession == null) ? (other.accession != null) : !this.accession.equals(other.accession)) {
                    returnValue = false;
                }
                if (this.sequence == null && other.sequence != null) {
                    if (!other.sequence.isEmpty() && this.sequence.equals(other.sequence)) {
                        returnValue = false;
                    }
                }
            }
        }
        return returnValue;
    }

    @Override
    public final boolean add(PeptideGroup e) {
        boolean added = false;
        if (this.contains(e)) {
            this.get(this.indexOf(e)).addAll(e);
        } else {
            added = super.add(e);
        }
        return added;
    }
}
