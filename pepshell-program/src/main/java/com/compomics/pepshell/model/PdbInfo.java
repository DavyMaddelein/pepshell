package com.compomics.pepshell.model;

/**
 *
 * @author Davy Maddelein
 */
public class PdbInfo {

    private String species;
    private String name;
    private String uniprotAccession;
    private String pdbAccession;
    private Double resolution;
    private String method;

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

    public Double getResolution() {
        return resolution;
    }

    public void setResolution(Double resolution) {
        this.resolution = resolution;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

//    @Override
//    public boolean equals(Object obj) {
//        boolean equal = false;
//        if (obj instanceof PdbInfo) {
//            if (((PdbInfo) obj).getPdbAccession().toLowerCase().equals(this.pdbAccession.toLowerCase())) {
//                equal = true;
//            }
//        }
//        return equal;
//    }
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + (this.pdbAccession != null ? this.pdbAccession.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PdbInfo other = (PdbInfo) obj;
        return !((this.pdbAccession == null) ? (other.getPdbAccession() != null) : !this.pdbAccession.equals(other.getPdbAccession()));
    }
    
    @Override
    public String toString() {
        return this.pdbAccession;
    }
}
