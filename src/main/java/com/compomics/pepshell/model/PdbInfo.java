package com.compomics.pepshell.model;

/**
 *
 * @author Davy
 */
public class PdbInfo {

    private String species;
    private String name;
    private String uniprotAccession;
    private String pdbAccession;

    public PdbInfo() {

    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUniprotAccession() {
        return uniprotAccession;
    }

    public void setUniprotAccession(String uniprotAccession) {
        this.uniprotAccession = uniprotAccession;
    }

    public String getPdbAccession() {
        return pdbAccession;
    }

    public void setPdbAccession(String pdbAccession) {
        this.pdbAccession = pdbAccession;
    }

    @Override
    public boolean equals(Object obj) {
        boolean equal = false;
        if (obj instanceof PdbInfo) {
            if (((PdbInfo) obj).getPdbAccession().equals(this.pdbAccession)) {
                equal = true;
            }
        }
        return equal;
    }

    @Override
    public String toString(){
        return this.pdbAccession;
    }
}
