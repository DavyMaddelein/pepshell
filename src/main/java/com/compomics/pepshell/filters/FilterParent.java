package com.compomics.pepshell.filters;

import com.compomics.pepshell.model.exceptions.InvalidOptionException;
import java.util.List;

/**
 *
 * @author Davy
 */
public abstract class FilterParent<T extends Object> {

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
    boolean stringent = false;

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
