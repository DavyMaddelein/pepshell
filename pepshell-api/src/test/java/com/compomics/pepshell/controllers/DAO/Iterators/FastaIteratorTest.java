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

package com.compomics.pepshell.controllers.DAO.Iterators;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.exceptions.FastaCouldNotBeReadException;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static junit.framework.TestCase.fail;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Davy Maddelein on 10/02/2015.
 */


public class FastaIteratorTest {

    private File testFasta = new File(ClassLoader.getSystemClassLoader().getResource("testfasta.fasta").getPath());
    private File notAFasta = new File(ClassLoader.getSystemClassLoader().getResource("tinytestfile.tsv").getPath());

    @Test
    public void TestFastaIterator() throws Exception {
        List<PepshellProtein> pepshellProteinContainer = new ArrayList<>(488);
        FastaIterator<PepshellProtein> it = new FastaIterator<>(testFasta);
        while (it.hasNext()) {
            pepshellProteinContainer.add(it.next());
        }
        assertThat(pepshellProteinContainer.size(), is(488));
        assertThat(pepshellProteinContainer, hasItem(new PepshellProtein("gi|00000022")));
        assertThat(pepshellProteinContainer, allOf(hasItem(new PepshellProtein("gi|00000021")), not(hasItem(new PepshellProtein("totallymadeup")))));
    }

    @Test
    public void TestFaultyFasta() {
        FastaIterator<PepshellProtein> it = null;
        try {
            it = new FastaIterator<>(new File("fake_file"));
        } catch (FastaCouldNotBeReadException e) {
            assertThat(e.getFailedFastaFileName(), is("fake_file"));
        }
    }

    @Test
    public void testNotAFastaFile() {
        FastaIterator<PepshellProtein> it = null;
        try {
            it = new FastaIterator<>(notAFasta);
        } catch (FastaCouldNotBeReadException e) {
            fail("should not have thrown an error here");
        }
        ObserverStub observerStub = new ObserverStub();
        FaultBarrier.getInstance().addObserver(observerStub);
        if (it.hasNext()) {
            //this should fail and trigger the faultbarrier
            PepshellProtein aPepshellProtein = it.next();
            fail("we shouldn't even have gotten a next");
        }
        assertThat(observerStub.getHopefullyFaultBarrier(),is(equalTo(FaultBarrier.getInstance())));
        assertThat(observerStub.getHopefullyMessage(),is((instanceOf(String.class))));
        assertThat(observerStub.getHopefullyMessage(),is(equalTo("file contains non fasta lines")));
    }

    public class ObserverStub implements Observer {

        Observable hopefullyFaultBarrier;
        Object hopefullyMessage;

        /**
         * This method is called whenever the observed object is changed. An
         * application calls an <tt>Observable</tt> object's
         * <code>notifyObservers</code> method to have all the object's
         * observers notified of the change.
         *
         * @param o   the observable object.
         * @param arg an argument passed to the <code>notifyObservers</code>
         */
        @Override
        public void update(Observable o, Object arg) {
            hopefullyFaultBarrier = o;
            hopefullyMessage = arg;
        }

        public Observable getHopefullyFaultBarrier() {
            return hopefullyFaultBarrier;
        }

        public Object getHopefullyMessage() {
            return hopefullyMessage;
        }
    }
}
