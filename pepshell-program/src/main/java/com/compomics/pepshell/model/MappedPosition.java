


package com.compomics.pepshell.model;

/**
 * a container for the start and stop location of an alignment
 * @author Davy Maddelein
 */
public class MappedPosition {
    private final int startingAlignmentPosition;
    private final int lastAlignmentPosition;

    
    public MappedPosition(int aFirstAlignmentPosition, int aLastAlignmentPosition){
        startingAlignmentPosition = aFirstAlignmentPosition;
        lastAlignmentPosition = aLastAlignmentPosition;
    }
    
    public int getStartingAlignmentPositions(){
        return startingAlignmentPosition;
    }
    
    public int getLastAlignmentPosition(){
        return lastAlignmentPosition;
    }
}
