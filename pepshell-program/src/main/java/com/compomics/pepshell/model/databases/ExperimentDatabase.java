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
public abstract class ExperimentDatabase {

    ExperimentDatabase(){
    }
    
    public abstract String getQuantForExperiment();

    public abstract String getPeptidesWithQuant();
    
    public abstract String selectAllProteins();

    public abstract String selectAllPeptidesGrouped();

    public abstract String selectAllExperiments();

    public abstract String selectASingleExperiment();

    public abstract String selectAllPeptidesGroupedForProteinAccession();
    
    public abstract String selectAllQuantedPeptideGroups();

    public abstract String ExperimentHasQuant();
    
    public abstract String getErrorForQuantedPeptide();
    
    public abstract String getQuantForPeptide();
    
}
