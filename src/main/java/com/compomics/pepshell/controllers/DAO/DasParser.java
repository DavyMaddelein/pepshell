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

    public static List<DasFeature> getAllDasFeatures(String webPage) throws XMLStreamException {
        List<DasFeature> allFeatures = new ArrayList<DasFeature>();
        allFeatures.add(new DasFeature(webPage));
        return allFeatures;
    }

    public static DasMethod getDasMethodForFeature(DasFeature feature) {
        return null;
    }
}
