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

package com.compomics.pepshell.model;

/**
 *
 * @author Davy Maddelein
 */
public class PdbInfo extends ProteinFeature {

    private String species;
    private String name;
    private String uniprotAccession;
    private String pdbAccession;
    private Double resolution;
    private String method;

    public PdbInfo(String description) {
        super(description);

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
