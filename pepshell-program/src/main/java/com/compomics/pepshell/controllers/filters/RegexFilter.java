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

package com.compomics.pepshell.controllers.filters;

import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Davy Maddelein
 */
public class RegexFilter extends FilterParent<String> {

    /**
     * a greedy filter that uses regexes to filter the supplied {@code List}
     *
     * @param listToFilter list of strings to run the regexes against
     * @param regexesToFilterAgainst a list of regexes to filter against,
     * inclusive: if one is matched it is added, exclusive if one matches it is
     * removed
     * @return
     */
    @Override
    public List<String> filter(List<String> listToFilter, List<String> regexesToFilterAgainst) {
        List<String> matchedItems = new ArrayList<>();
        for (String aRegex : regexesToFilterAgainst) {
            if (inclusive) {
                matchedItems.addAll(Collections2.filter(listToFilter, Predicates.contains(Pattern.compile(aRegex))));
            } else {
                matchedItems.addAll(Collections2.filter(listToFilter, Predicates.not(Predicates.contains(Pattern.compile(aRegex)))));
            }
        }
        return matchedItems;
    }

    public List<PepshellProtein> filterProtein(List<? extends PepshellProtein> listToFilter, List<String> regexesToFilterAgainst) {
        List<PepshellProtein> matchedItems = new ArrayList<>();
        /**
         * for (String aRegex : regexesToFilterAgainst) { if (inclusive) {
         * matchedItems.addAll(Collections2.filter(listToFilter,
         * Predicates.contains(Pattern.compile(aRegex)))); } else {
         * matchedItems.addAll(Collections2.filter(listToFilter,
         * Predicates.not(Predicates.contains(Pattern.compile(aRegex))))); } }
         */
        for (String aString : regexesToFilterAgainst) {
            for (PepshellProtein aPepshellProtein : listToFilter) {
                if (aPepshellProtein.getVisibleAccession().matches(aString)) {
                    matchedItems.add(aPepshellProtein);
                }
            }
        }
        return matchedItems;
    }

    private List<String> stringentFilter(List<String> listToFilter, List<String> regexesToFilterAgainst) {
        List<String> matchedItems = listToFilter;
        for (String aRegex : regexesToFilterAgainst) {
            matchedItems = Lists.newArrayList(Collections2.filter(matchedItems, Predicates.contains(Pattern.compile(aRegex))));
        }
        return matchedItems;
    }
}
