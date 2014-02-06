package com.compomics.pepshell.model;

import com.google.common.collect.ImmutableBiMap;

/**
 *
 * @author Davy
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
