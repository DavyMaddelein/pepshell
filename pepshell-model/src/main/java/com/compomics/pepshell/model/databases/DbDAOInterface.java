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

package com.compomics.pepshell.model.databases;

import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Davy Maddelein on 21/04/2015.
 */
public interface DbDAOInterface {

    <T extends Experiment> List<PepshellProtein> fetchProteins(T experiment, Connection aConnection) throws SQLException;

    <T extends Experiment> T fetchPeptidesAndProteins(T experiment, Connection aConnection) throws SQLException, IOException;

    <E extends PepshellProtein> List<E> addPeptideGroupsToProteins(List<E> proteins, Connection aConnection) throws SQLException;

    List<? extends PeptideGroup> getPeptideGroupsForAccession(String proteinAccession, int projectid, Connection aConnection) throws SQLException;

    boolean projectHasQuant(Experiment anExperiment, Connection aConnection) throws SQLException;

    List<? extends Experiment> getExperiments(Connection aConnection) throws SQLException;

    Experiment getExperiment(int experimentId, boolean addProteins, Connection aConnection) throws SQLException, NullPointerException, URISyntaxException, MalformedURLException, IOException;

    boolean experimentIsQuanted(Experiment project, Connection aConnection) throws SQLException;

    Double getRatioForPeptide(int anIdentificationId, Connection aConnection) throws SQLException;

    Double setErrorForPeptide(int anIdentificationId, Connection aConnection) throws SQLException;
}

