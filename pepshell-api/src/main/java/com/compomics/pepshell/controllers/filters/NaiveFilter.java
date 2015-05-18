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

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Davy Maddelein
 * @param <T>
 */
public class NaiveFilter<T> extends FilterParent<T> {

    //as the title says, pretty naive
    @Override
    public List<T> filter(List<T> listToFilter, List<T> listToFilterAgainst) {
        if (inclusive) {
            return listToFilter.stream().filter(listToFilterAgainst::contains).collect(Collectors.toList());
        } else {
            return listToFilter.stream().filter(e -> !listToFilterAgainst.contains(e)).collect(Collectors.toList());
        }
    // no real need for stringent checks since, being present should be stringent enough
    }

}
