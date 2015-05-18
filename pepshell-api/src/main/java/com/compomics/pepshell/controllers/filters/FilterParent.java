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

import com.compomics.pepshell.model.exceptions.InvalidOptionException;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
public abstract class FilterParent<T> {

    public enum FilterMode {

        INCLUSIVE(1),
        EXCLUSIVE(2),
        LAX(3),
        STRINGENT(4);

        int value;

        private FilterMode(int mode) {
            this.value = mode;
        }

        int getValue() {
            return value;
        }
    }

    boolean inclusive = true;
    private boolean stringent = false;

    public void setMode(FilterMode aFilterMode) throws InvalidOptionException {
        if (aFilterMode == FilterMode.INCLUSIVE || aFilterMode == FilterMode.EXCLUSIVE) {
            this.inclusive = (aFilterMode.value == FilterMode.INCLUSIVE.value);
        } else if (aFilterMode == FilterMode.STRINGENT || aFilterMode == FilterMode.LAX) {
            this.stringent = (aFilterMode.value == FilterMode.STRINGENT.value);
        } else {
            throw new InvalidOptionException("invalid option given");
        }
    }

    public abstract List<T> filter(List<T> listToFilter, List<T> listToFilterAgainst);
}
