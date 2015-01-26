/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
