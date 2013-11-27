package com.compomics.peppi.model;

import java.util.ArrayList;

/**
 *
 * @author Davy
 */
public class AnalysisGroup extends ArrayList<Project> {
    
    private String analysisName;
    
    public AnalysisGroup(String analysisName){
        super();
        this.analysisName = analysisName;
    }
 
    public String getName(){
        return analysisName;
    }
    
    @Override
    public String toString(){
        return analysisName;
    }
}
