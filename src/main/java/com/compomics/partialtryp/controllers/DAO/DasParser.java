package com.compomics.partialtryp.controllers.DAO;

import com.compomics.partialtryp.model.DAS.DasFeature;
import com.compomics.partialtryp.model.DAS.DasMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Davy
 * Date: 3/4/13
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class DasParser {
    //TODO convert to saxparser

    public static List<DasFeature> getAllDasFeatures (String dasXMLFile) {
        List<DasFeature> allFeatures = new ArrayList<DasFeature>();
        int lastFeatureEndPosition = 0;
        while(dasXMLFile.indexOf("<FEATURE",lastFeatureEndPosition + 9) != -1){
            String feature = dasXMLFile.substring(dasXMLFile.indexOf("<FEATURE",lastFeatureEndPosition + 9),dasXMLFile.indexOf("</FEATURE>",lastFeatureEndPosition + 9) +10 );
            lastFeatureEndPosition = dasXMLFile.indexOf("</FEATURE>",lastFeatureEndPosition + 9);
            if(feature.indexOf("<NOTE>No features found for the segment</NOTE>")< 0){
                allFeatures.add(new DasFeature(feature));
            }
        }
    return allFeatures;
    }
    
    public static DasMethod getDasMethodForFeature(DasFeature feature) {
        return null;
    }
}
