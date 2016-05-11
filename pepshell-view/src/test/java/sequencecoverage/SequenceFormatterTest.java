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

package sequencecoverage;

import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.FeatureWithLocation;
import com.compomics.pepshellstandalone.dataviewing.sequencecoverage.SequenceFormatter;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by Davy Maddelein on 08/04/2015.
 */
public class SequenceFormatterTest {

    @Test
    public void testFormatSequenceString() throws Exception {

        SequenceFormatter test = new SequenceFormatter();
        String result = test.formatSequenceString("AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDE");
        assertThat(result, is(notNullValue()));
        assertThat(result.length(), allOf(is(not(0)), (is(45))));
        assertThat(result.split("\t").length, is(5));
        assertThat(Arrays.asList(result.split("\t")), hasItems("AAAAAAAAAA", "BBBBBBBBBB", "CCCCCCCCCC", "DDDDDDDDDD", "E"));
    }

    @Test
    public void testCustomFormatSequenceString() throws Exception {
        SequenceFormatter test = new SequenceFormatter();
        test.setDividingCharacter('@');
        test.setAminoAcidNumberBetweenEachDivide(5);
        String result = test.formatSequenceString("AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDE");
        assertThat(result, is(notNullValue()));
        assertThat(result.length(), allOf(is(not(0)), (is(49))));
        assertThat(result.split("\t").length, is(1));
        assertThat(result.split("@").length, is(9));
        assertThat(Arrays.asList(result.split("@")), hasItems("AAAAA", "BBBBB", "CCCCC", "DDDDD", "E"));


    }

    @Test
    public void testFormatSequenceWithProteinInformation() throws Exception {

        PepshellProtein testProtein = new PepshellProtein("test", "AAAAAAAAAABBBBBBBBBBCCCCCCCCCCDDDDDDDDDDE");
        testProtein.addDomains(new ArrayList<FeatureWithLocation>() {{
            this.add(new FeatureWithLocation("testdomain", 5, 10));
            this.add(new FeatureWithLocation("testdomain2", 15, 16));
        }});

        testProtein.addPeptideGroup(new PeptideGroup(new Peptide("CCC", 11)));

        SequenceFormatter test = new SequenceFormatter();
        String result = test.formatSequenceWithProteinInformation(testProtein);

        assertThat(result, is(notNullValue()));
        assertThat(result.length(), allOf(is(not(0)), (is(49))));

    }

    @Test
    public void testAddFeatures() throws Exception {

    }
}