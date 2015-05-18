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
package com.compomics.pepshell.model.enums;

/**
 * @author Davy Maddelein
 */
public enum DomainWebsitesEnum implements PropertyEnum {

    PFAM("http://das.sanger.ac.uk/das/pfam/features?segment="),
    SMART("http://smart.embl.de/smart/das/smart/features?segment="),
    //PROSITE("http://proserver.vital-it.ch/das/prositefeature/features?segment="),
    UNIPROT("https://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/features?segment=");
    //UNIPROT("http://www.uniprot.org/uniprot/P15056.xml");

    public String getDomainURLString(String aUniProtAccession) {
        return rootURL + aUniProtAccession;
    }

    String rootURL;

    DomainWebsitesEnum(String aRootURL) {
        this.rootURL = aRootURL;
    }

    @Override
    public String getKey() {
        return this.name();
    }

    @Override
    public String getDefaultValue() {
        return rootURL;
    }

}
