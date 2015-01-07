package com.compomics.pepshell.model;

/**
 *
 * @author Davy Maddelein
 */
//TODO this needs a better name, this stores temporary data about the project comparisons
public class ProteinInfo {

   private int numberOfProjectOccurences = 0;
    
   
   public void increaseNumberOfProjectOccurences(){
       numberOfProjectOccurences++;
   }
   
   public int getNumberOfProjectOccurences(){
       return numberOfProjectOccurences;
   }
}
