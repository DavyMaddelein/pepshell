package com.compomics.pepshell.controllers.filters;

import com.compomics.pepshell.model.Protein;
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

    public List<Protein> filterProtein(List<Protein> listToFilter, List<String> regexesToFilterAgainst) {
        List<Protein> matchedItems = new ArrayList<>();
        /**
         * for (String aRegex : regexesToFilterAgainst) { if (inclusive) {
         * matchedItems.addAll(Collections2.filter(listToFilter,
         * Predicates.contains(Pattern.compile(aRegex)))); } else {
         * matchedItems.addAll(Collections2.filter(listToFilter,
         * Predicates.not(Predicates.contains(Pattern.compile(aRegex))))); } }
         */
        for (String aString : regexesToFilterAgainst) {
            for (Protein aProtein : listToFilter) {
                if (aProtein.getVisibleAccession().matches(aString)) {
                    matchedItems.add(aProtein);
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
