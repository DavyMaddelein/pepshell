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

/**
 * Created by Davy Maddelein on 22/02/2015.
 */
public class SequenceEncoder {


    private SequenceEncoder(){
        String storageStrategyClassName = ProgramProperties.getInstance().getProperty("default.aminoacid.delta.map");
        if (storageStrategyClassName != null && !storageStrategyClassName.isEmpty()) {
            try {
                ClassLoader.getSystemClassLoader().loadClass(storageStrategyClassName);
            } catch (ClassNotFoundException e) {
                FaultBarrier.getInstance().handleException(e);
            }
        }
    }

    public static byte[] toByteEncoding(String aString){
        byte[] translatedArray = new byte[aString.toCharArray().length];
        for (int i = 0; i < aString.toCharArray().length; i++) {
            translatedArray[i] = (byte) (aString.charAt(i) - 'A');
        }
      return translatedArray;
    }

    public static String fromByteEncoding(byte[] sequenceByteArray){
        StringBuilder translatedSequence = new StringBuilder();
        for(byte aByte : sequenceByteArray){
            translatedSequence.append((char)(aByte+'A'));
        }
        return translatedSequence.toString();
    }

    //as we cannot sort the byte arrays doing actual delta encoding is a bit pointless
//    public byte[] deltaEncode(String aSequence){
//        byte[] toEncode = toByteEncoding(aSequence);
//        Arrays.sort(toEncode);
//        for( int i =1; i < toEncode.length; i++){
//            toEncode[i] = (byte) (toEncode[i]- toEncode[i-1]);
//        }
//        return toEncode;
//    }
}
