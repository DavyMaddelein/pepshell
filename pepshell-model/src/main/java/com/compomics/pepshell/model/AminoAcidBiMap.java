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

import org.apache.commons.collections.bidimap.DualHashBidiMap;

import java.util.HashMap;


/**
 *
 * @author Davy Maddelein
 */
public class AminoAcidBiMap {

    private static final DualHashBidiMap AminoAcidLetters = new DualHashBidiMap(new HashMap<String,String>(){
            {
                    this.put("A", "ALA");
                    this.put("C", "CYS");
                    this.put("D", "ASP");
                    this.put("E", "GLU");
                    this.put("F", "PHE");
                    this.put("G", "GLY");
                    this.put("H", "HIS");
                    this.put("I", "ILE");
                    this.put("K", "LYS");
                    this.put("L", "LEU");
                    this.put("M", "MET");
                    this.put("N", "ASN");
                    this.put("P", "PRO");
                    this.put("Q", "GLN");
                    this.put("R", "ARG");
                    this.put("S", "SER");
                    this.put("T", "THR");
                    this.put("V", "VAL");
                    this.put("W", "TRP");
                    this.put("Y", "TYR");
            }});

        public static String getOneLetterToThreeLetter(String oneLetterAA){
                return (String)AminoAcidLetters.get(oneLetterAA);
        }

        public static String getThreeLetterToOneLetter (String threeLetterAA){
                return (String) AminoAcidLetters.inverseBidiMap().get(threeLetterAA);
        }
}
