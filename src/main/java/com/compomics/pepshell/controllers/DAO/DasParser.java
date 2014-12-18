package com.compomics.pepshell.controllers.DAO;

import com.compomics.pepshell.model.DAS.DasFeature;
import com.compomics.pepshell.model.DAS.DasMethod;

import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/4/13 Time: 11:29 AM To change
 * this template use File | Settings | File Templates.
 */
public class DasParser {

    public static List<DasFeature> getAllDasFeatures(String webPage){
        List<DasFeature> allFeatures = new ArrayList<>();
        String[] allSplitfeatures = webPage.split("<FEATURE");
        for (int i = 1; i < allSplitfeatures.length; i++) {
            allFeatures.add(new DasFeature(allSplitfeatures[i]));
        }
        return allFeatures;
    }

    public static DasMethod getDasMethodForFeature(DasFeature feature) {
        return null;
    }
}
