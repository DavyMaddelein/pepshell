package com.compomics.pepshell;

import com.compomics.pepshell.model.HydrophobicityMaps;
import java.awt.Color;
import java.util.Map;

/**
 *
 * @author Davy
 */
public class ProgramVariables {

    public static Color PROTEINCOLOR = Color.RED;
    public static Color PEPTIDECOLOR = Color.BLUE;
    public static Color DOMAINCOLOR = Color.GREEN;
    public static double SCALE = 1;
    public static int VERTICALSIZE = 15;
    public static Map<String, Color> AMINOACIDGRADIENT = HydrophobicityMaps.hydrophobicityMapPh7;
}
