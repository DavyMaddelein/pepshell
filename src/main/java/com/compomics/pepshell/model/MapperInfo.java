/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.model;

/**
 *
 * @author Davy
 */
public class MapperInfo {
    private int startingAlignmentPosition;
    private int lastAlignmentPosition;

    
    public int getStartingAlignmentPosition(){
        return this.startingAlignmentPosition;
    }
    
    public int getLastAlignmentPosition(){
        return this.lastAlignmentPosition;
    }

    public void setStartingAlignmentPosition(int startingAlignmentPosition) {
        this.startingAlignmentPosition = startingAlignmentPosition;
    }

    public void setEndingAlignmentPosition(int lastAlignmentPosition) {
        this.lastAlignmentPosition = lastAlignmentPosition;
    }
    
    
}
