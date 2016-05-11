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

package com.compomics.pepshell.controllers.comparators;

import java.util.Comparator;
import java.util.HashSet;

/**
 *
 * @author Davy Maddelein
 */
public class NumbersComparator implements Comparator<Number> {

    private HashSet<Class<? extends Number>> allowedTypes;

    {
        allowedTypes = new HashSet<>();
        allowedTypes.add(Integer.class);
        allowedTypes.add(Double.class);
        allowedTypes.add(Float.class);
        allowedTypes.add(Short.class);
        allowedTypes.add(Byte.class);

    }

    /**
     * compares two {@link Number}s
     * @param firstNumber first number to compare
     * @param secondNumber second number to compare
     * @Throws UnsupportedOperationException when the passed numbers are not of the type integer,double,float,short or byte
     * @return positive if firstNumber is greater than secondNumber, 0 if both are equal, negative if secondNumber is greater than firstNumber
     */
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