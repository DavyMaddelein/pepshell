package com.compomics.peppi.controllers.comparators;

import com.compomics.peppi.model.DAS.DasFeature;
import java.io.Serializable;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/6/13 Time: 1:36 PM To change
 * this template use File | Settings | File Templates.
 */
public class CompareDasFeatures implements Comparator<DasFeature>, Serializable {

    public int compare(DasFeature dasFeature1, DasFeature dasFeature2) {
        int returnValue;
        if (dasFeature1.getEnd() < dasFeature2.getEnd()) {
            returnValue = -1;
        } else if (dasFeature1.getEnd() > dasFeature2.getEnd()) {
            returnValue = 1;
        } else {
            returnValue = 0;
        }
        return returnValue;
    }
}
