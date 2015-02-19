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

import java.util.List;
import java.util.Set;

/**
 *
 * @author Davy Maddelein
 */
public interface ProteinInterface {
    //TODO move the pepshell specific things to the protein object (like pdb info, domains, CPDT, ...)

    void addDomains(List<ProteinFeatureWithLocation> domainsToAdd);

    void addPdbFileInfo(Set<PdbInfo> allPDBFileNamesForProtein);

    Protein addPeptideGroup(PeptideGroup aPeptideGroup);

    Protein addPeptideGroups(List<PeptideGroup> aListOfPeptideGroups);

    List<ProteinFeatureWithLocation> getDomains();

    String getOriginalAccession();

    Set<PdbInfo> getPdbFilesInfo();

    List<PeptideGroup> getPeptideGroups();

    int getProjectid();

    String getProteinAccession();

    ProteinInfo getProteinInfo();

    String getProteinName();

    String getProteinSequence();

    String getVisibleAccession();

    void setAccession(String anAccession);

    void setCPDTPeptideList(List<PeptideGroup> CPDTList);

    List<PeptideGroup> getCPDTPeptideList();

    <T extends PeptideGroup> void setPeptideGroupsForProtein(List<T> listOfPeptides);

    void setProjectId(int projectId);

    void setProteinInfo(ProteinInfo proteinInfo);

    void setProteinName(String aProteinName);

    void setSequence(String sequence);

    void setVisibleAccession(String visibleAccession);
    
}
