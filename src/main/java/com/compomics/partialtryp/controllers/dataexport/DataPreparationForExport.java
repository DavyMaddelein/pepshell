package com.compomics.partialtryp.controllers.dataexport;

import java.util.List;

/**
 *
 * @author Davy
 */
public class DataPreparationForExport {

    public static String returnListCommaSeparated(List<?> listToSeparate) {
        StringBuilder pdbAccessions = new StringBuilder();
        for (Object object : listToSeparate) {
            pdbAccessions.append(object.toString());
            pdbAccessions.append(",");
        }
        pdbAccessions.deleteCharAt(pdbAccessions.length());
        return pdbAccessions.toString();
    }
}
