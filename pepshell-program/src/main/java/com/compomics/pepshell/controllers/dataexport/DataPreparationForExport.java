package com.compomics.pepshell.controllers.dataexport;

import java.util.Collection;

/**
 *
 * @author Davy Maddelein
 */
class DataPreparationForExport {

    /**
     * this takes a list of objects and returns the toString representation of
     * it in a comma separated String
     *
     * @param listToSeparate list of objects to Stringify
     * @return String of comma separated toString values
     */
    public static String returnListCommaSeparated(Collection<?> listToSeparate) {
        StringBuilder separatedList = new StringBuilder();
        for (Object object : listToSeparate) {
            separatedList.append(object.toString());
            separatedList.append(",");
        }
        separatedList.deleteCharAt(separatedList.length());
        return separatedList.toString();
    }
}
