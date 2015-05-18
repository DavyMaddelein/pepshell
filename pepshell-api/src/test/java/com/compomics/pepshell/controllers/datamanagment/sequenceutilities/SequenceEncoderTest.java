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

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Davy Maddelein on 23/02/2015.
 */
public class SequenceEncoderTest {


    @Test
    public void fromByteEncoding(){
        byte[] bytearray = new byte[]{12,21,16,21,11,4,11};
        String reversedSequence = SequenceEncoder.fromByteEncoding(bytearray);
        assertThat(reversedSequence, is(equalTo("MVQVLEL")));
    }


    @Test
    public void toByteEncoding(){
        byte[] bytearray = SequenceEncoder.toByteEncoding("MVQVLEL");
        assertThat(bytearray, is(equalTo(new byte[]{12,21,16,21,11,4,11})));
    }
}
