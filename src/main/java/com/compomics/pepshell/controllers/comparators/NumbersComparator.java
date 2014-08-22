package com.compomics.pepshell.controllers.comparators;

import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Davy
 */
public class NumbersComparator implements Comparator<Number> {

    HashSet<Class<? extends Number>> allowedTypes;

    {
        allowedTypes = new HashSet<Class<? extends Number>>();
        allowedTypes.add(Integer.class);
        allowedTypes.add(Double.class);
        allowedTypes.add(Float.class);
        allowedTypes.add(Short.class);
        allowedTypes.add(Byte.class);

    }

    public int compare(Number firstNumber, Number secondNumber) {

        Double d1 = (firstNumber == null) ? Double.POSITIVE_INFINITY : firstNumber
                .doubleValue();
        Double d2 = (secondNumber == null) ? Double.POSITIVE_INFINITY : secondNumber
                .doubleValue();
        if (firstNumber != null && secondNumber != null) {
            if (!(allowedTypes.contains(firstNumber.getClass()) && allowedTypes
                    .contains(secondNumber.getClass()))) {
                throw new UnsupportedOperationException(
                        "Allowed Types:" + allowedTypes);
            }
        }
        return d1.compareTo(d2);
    }
}