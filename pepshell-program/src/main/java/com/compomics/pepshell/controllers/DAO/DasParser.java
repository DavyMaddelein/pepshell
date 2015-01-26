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
