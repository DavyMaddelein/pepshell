package com.compomics.peppi.controllers.dataexport;

import java.util.List;

/**
 *
 * @author Davy
 */
public class DataPreparationForExport {

    /**
     * this takes a list of objects and returns the toString representation of it in a comma separated String
     * @param listToSeparate list of objects to Stringify
     * @return String of comma separated toString values
     */
    public static String returnListCommaSeparated(List<?> listToSeparate) {
        StringBuilder separatedList = new StringBuilder();
        for (Object object : listToSeparate) {
            separatedList.append(object.toString());
            separatedList.append(",");
        }
        separatedList.deleteCharAt(separatedList.length());
        return separatedList.toString();
    }
}
