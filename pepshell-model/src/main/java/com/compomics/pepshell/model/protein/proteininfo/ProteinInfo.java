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

package com.compomics.pepshell.model.protein.proteininfo;

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
