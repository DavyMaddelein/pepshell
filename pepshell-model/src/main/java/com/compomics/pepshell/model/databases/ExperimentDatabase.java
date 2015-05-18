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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.model.databases;

/**
 *
 * @author Davy Maddelein
 */
public interface ExperimentDatabase {
    
    String getQuantForExperiment();

    String getPeptidesWithQuant();
    
    String selectAllProteins();

    String selectAllPeptidesGrouped();

    String selectAllExperiments();

    String selectASingleExperiment();

    String selectAllPeptidesGroupedForProteinAccession();
    
    String selectAllQuantedPeptideGroups();

    String ExperimentHasQuant();
    
    String getErrorForQuantedPeptide();
    
    String getQuantForPeptide();
    
}
