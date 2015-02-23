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

package com.compomics.pepshell.controllers.datamanagment.sequenceutilities;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.properties.ProgramProperties;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Davy Maddelein on 22/02/2015.
 */
public class DeltaSequenceEncoder {


    private static DeltaSequenceEncoder encoder;
    /**
     * replace with bidirectional hashmap
     */
    private static HashMap<Character,Integer> map = new HashMap(){{
        put('L',1);
        put('I', 2);
        put('F', 3);
        put('W', 4);
        put('V', 5);
        put('M', 6);
        put(Character.getNumericValue('Y'), 7);
        put(Character.getNumericValue('C'), 8);
        put(Character.getNumericValue('A'), 9);
        put(Character.getNumericValue('T'), 10);
        put(Character.getNumericValue('H'), 11);
        put(Character.getNumericValue('G'), 12);
        put(Character.getNumericValue('S'), 13);
        put(Character.getNumericValue('Q'), 14);
        put(Character.getNumericValue('R'), 15);
        put(Character.getNumericValue('K'), 16);
        put(Character.getNumericValue('N'), 17);
        put(Character.getNumericValue('E'), 18);
        put(Character.getNumericValue('P'), 19);
        put(Character.getNumericValue('D'), 20);
    }};

    public static DeltaSequenceEncoder getInstance(){
        if (encoder == null){
            encoder = new DeltaSequenceEncoder();
        }
        return encoder;
    }

    private DeltaSequenceEncoder(){
        String storageStrategyClassName = ProgramProperties.getInstance().getProperty("default.aminoacid.delta.map");
        if (storageStrategyClassName != null) {
            try {
                ClassLoader.getSystemClassLoader().loadClass(storageStrategyClassName);
            } catch (ClassNotFoundException e) {
                FaultBarrier.getInstance().handleException(e);
            }
        }
    }

    /**
     * takes an array of bytes with the assumption that the absolute number is at the front of the array and everything else is dependent on the previous value in the array
     * @param byteSequence the byte sequence to transform into a textual amino acid sequence
     * @return the readable amino acid sequence
     */
    public static String fromByteEncoding(byte[] byteSequence){
        StringBuilder builder = new StringBuilder(map.get(byteSequence[0]));
        for (int i = 1; i < byteSequence.length; i++) {
            builder.append(map.get(byteSequence[i] + byteSequence[i -1]));
        }
        return builder.toString();
    }

    public static byte[] toByteEncoding(String aString){
        byte[] translatedArray = new byte[aString.toCharArray().length];
        for (int i = 0; i < aString.toCharArray().length; i++) {
            Character aChar = aString.charAt(i);
            Integer value = map.get(aChar);
            translatedArray[i] = value.byteValue();

        }
      return translatedArray;
    }

    public static void setTranslationKey(HashMap<Character,Integer> newTranslationKey){
        map.clear();
        map.putAll(newTranslationKey);
    }
}
