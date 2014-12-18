/*
 *
 *  * Copyright 2014 Davy Maddelein.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.compomics.pepshell.model;

import com.google.common.collect.ImmutableBiMap;

/**
 *
 * @author Davy Maddelein
 */
public class AminoAcidBiMap {

    public static final ImmutableBiMap<String, String> AminoAcidLetters
            = new ImmutableBiMap.Builder<String, String>()
            .put("A", "ALA")
            .put("C", "CYS")
            .put("D", "ASP")
            .put("E", "GLU")
            .put("F", "PHE")
            .put("G", "GLY")
            .put("H", "HIS")
            .put("I", "ILE")
            .put("K", "LYS")
            .put("L", "LEU")
            .put("M", "MET")
            .put("N", "ASN")
            .put("P", "PRO")
            .put("Q", "GLN")
            .put("R", "ARG")
            .put("S", "SER")
            .put("T", "THR")
            .put("V", "VAL")
            .put("W", "TRP")
            .put("Y", "TYR")
            .build();
}
