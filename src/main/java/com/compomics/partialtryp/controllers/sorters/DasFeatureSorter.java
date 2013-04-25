package com.compomics.partialtryp.controllers.sorters;

import com.compomics.partialtryp.model.DAS.DasFeature;
import java.io.Serializable;

import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Davy
 * Date: 3/6/13
 * Time: 1:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class DasFeatureSorter implements Comparator<DasFeature>,Serializable {

    public int compare(DasFeature dasFeature1, DasFeature dasFeature2) {
        if(dasFeature1.getEnd() < dasFeature2.getEnd()){
            return -1;
        }  else if (dasFeature1.getEnd() > dasFeature2.getEnd()){
            return 1;
        }  else {
            return 0;
        }
    }
}
