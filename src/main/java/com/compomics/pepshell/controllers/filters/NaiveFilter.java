package com.compomics.pepshell.controllers.filters;

import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import java.util.List;

/**
 *
 * @author Davy
 * @param <T>
 */
public class NaiveFilter<T extends Object> extends FilterParent<T> {

    //as the title says, pretty naive
    @Override
    public List<T> filter(List<T> listToFilter, List<T> listToFilterAgainst) {
        if (inclusive) {
            return Lists.newArrayList(Collections2.filter(listToFilter, Predicates.in(listToFilterAgainst)));
        } else {
            return Lists.newArrayList(Collections2.filter(listToFilter, Predicates.not(Predicates.in(listToFilterAgainst))));
        }
    // no real need for stringent checks since, being present should be stringent enough
    }

}
