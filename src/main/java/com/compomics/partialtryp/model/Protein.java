/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class Protein {

    private ProteinInfo proteinInfo;
    private List<PeptideGroup> peptideGroupsOfProtein = new ArrayList<PeptideGroup>();
    private final String accession;
    private int projectId;
    private String sequence;
    private List<Domain> domainsFoundInProtein = new ArrayList<Domain>();
    private List<String> allPDBFileNamesForProtein = new ArrayList<String>();


    public Protein(String accession){
        this.accession = accession;
    } 
    
    public String getProteinSequence() {
        return this.sequence;
    }

    public String getProteinAccession(){
        return this.accession;
    }

    public List<Domain> getDomains(){
        return domainsFoundInProtein;
    }

    public void addDomains(List<Domain> domainsToAdd){
        this.domainsFoundInProtein.addAll(domainsToAdd);
    }
    
    public List<PeptideGroup> getPeptideGroupsForProtein(){
        return this.peptideGroupsOfProtein;
    }
    
    public void setPeptideGroupsForProtein(List<PeptideGroup> listOfPeptides){
        this.peptideGroupsOfProtein = listOfPeptides;
    }
    
    public void setProjectId(int projectId){
        this.projectId = projectId;
    }
    
    public int getProjectid(){
        return this.projectId;
    }
    
    public void setProteinInfo(ProteinInfo proteinInfo){
        this.proteinInfo = proteinInfo;
    }
    
    public ProteinInfo getProteinInfo(){
        return this.proteinInfo;
    }
    
    public void setSequence(String sequence){
        this.sequence = sequence;
    }
    
    @Override
    public String toString(){
        return accession;
    }

    public void addPdbFileNames(List<String> allPDBFileNamesForProtein) {
        this.allPDBFileNamesForProtein.addAll(allPDBFileNamesForProtein);
    }
    
    public List<String> getPdbFileNames(){
        return allPDBFileNamesForProtein;
    }
}
