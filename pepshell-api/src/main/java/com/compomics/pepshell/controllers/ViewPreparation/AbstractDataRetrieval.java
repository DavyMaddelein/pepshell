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

package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AccessionConverting;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AddDomains;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AddPdbInfo;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.CPDTAnalysis;
import com.compomics.pepshell.model.DataModes.DataRetrievalInterface;
import com.compomics.pepshell.model.DataModes.DataRetrievalStep;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

import java.awt.Color;

import static java.lang.Thread.sleep;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

/**
 * prepares the data for the viewing part of pepshell
 *
 * @param <T> the type of experiment to prepare for
 * @author Davy Maddelein
 */
public interface AbstractDataRetrieval<T extends Experiment> extends Observer, DataRetrievalInterface<T> {

    LinkedList<DataRetrievalStep> linkedSteps = new LinkedList<DataRetrievalStep>() {
        {
            //default list
            this.add(new AccessionConverting());
            this.add(new AddDomains());
            this.add(new AddPdbInfo());
            this.add(new CPDTAnalysis());
        }
    };

    ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    JPanel progressMonitorPanel = new JPanel();
    JButton skipButton = new JButton();

    ProgressMonitor progressMonitor = new ProgressMonitor(progressMonitorPanel, skipButton, "retrieving data", 0, 101);


    /**
     * retrieve the protein data and all secondary data annotations for a given experiment
     *
     * @param referenceExperiment the experiment to add data to
     * @return the experiment that was passed along
     */
    default T retrieveData(T referenceExperiment) throws DataRetrievalException {
        progressMonitorPanel.setBackground(Color.white);
        progressMonitorPanel.setOpaque(true);
        skipButton.setText("skip current retrieval step");
        return retrievePrimaryData(referenceExperiment);
    }

    /**
     * adds the protein and peptide information to the experiment
     *
     * @param referenceExperiment the experiment to add the information to
     * @return the experiment passed to the method
     */
    T retrievePrimaryData(T referenceExperiment) throws DataRetrievalException;

    /**
     * retrieve any secondary data steps set for retrieval
     *
     * @param experiment the experiment to retrieve the secondary data for
     */
    default void retrieveSecondaryData(T experiment) throws DataRetrievalException{
            new RunRetrievalSteps(experiment, getDataRetrievalSteps(), this).execute();
        }


    @Override
    default void setDataRetrievalSteps(LinkedList<DataRetrievalStep> retrievalStepsToSet) {
        AbstractDataRetrieval.linkedSteps.clear();
        AbstractDataRetrieval.linkedSteps.addAll(retrievalStepsToSet);
    }

    @Override
    default Collection<DataRetrievalStep> getDataRetrievalSteps() {
        return Collections.unmodifiableCollection(linkedSteps);
    }

    //can be moved to separate class
    class RunRetrievalSteps extends SwingWorker<Boolean, Void> {

        private Observer observer;
        private Experiment experiment;
        private Collection<DataRetrievalStep> stepsToExecute;

        /**
         * creates an instance of a swingworker that will run the passed along retrieval steps on the experiment
         *
         * @param anExperiment   the experiment to run over
         * @param anObserver     optional observer to report progress to
         * @param retrievalSteps the steps to execute
         */
        public RunRetrievalSteps(Experiment anExperiment, Collection<DataRetrievalStep> retrievalSteps, Observer anObserver) {
            observer = anObserver;
            experiment = anExperiment;
            stepsToExecute = retrievalSteps;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            //TODO add preliminary checks to see if the server is accessible, if not, skip and warn user       
            //this is a silly work around
            final boolean[] skipStep = {false};

            skipButton.addActionListener(e -> skipStep[0] = true);

            /**
             * the logic for splitting up the tasks and then splitting up the
             * list of proteins, is that this way skipping unwanted steps
             * becomes granular and we can still multi thread each step instead
             * of front loading the list and then terminating whatever steps are
             * left or filtering the step that has to be skipped out of the
             * executor queue
             *
             */
            List<PepshellProtein> proteins = experiment.getProteins();
            List<List<PepshellProtein>> partitionedProteinList = new ArrayList<>();
            for (int i = 0; i < proteins.size();i += proteins.size() / Runtime.getRuntime().availableProcessors()){
                if (i + proteins.size() / Runtime.getRuntime().availableProcessors() > proteins.size()){
                    partitionedProteinList.add(proteins.subList(i,proteins.size()));
                } else {
                    partitionedProteinList.add(proteins.subList(i,i+Runtime.getRuntime().availableProcessors()));
                }
            }

            int totalTasks = (partitionedProteinList.size() * stepsToExecute.size());

            progressMonitor.setMaximum(totalTasks + 2);

            for (DataRetrievalStep aStep : stepsToExecute) {
                List<Future<List<PepshellProtein>>> taskList = new ArrayList<>();
                partitionedProteinList.stream().map(aStep::getInstance).map((toExecute) -> {
                    toExecute.addObserver(observer);
                    return toExecute;
                }).forEach((toExecute) -> taskList.add(executor.submit(toExecute)));

                while (!taskList.isEmpty()) {
                    if (skipStep[0]) {
                        taskList.stream().forEach((aFuture) -> aFuture.cancel(true));
                        skipStep[0] = false;
                    } else {
                        Iterator<Future<List<PepshellProtein>>> futureIter = taskList.listIterator();
                        while (futureIter.hasNext()) {
                            Future<List<PepshellProtein>> aFuture = futureIter.next();
                            if (aFuture.isDone() || aFuture.isCancelled()) {
                                futureIter.remove();
                                totalTasks--;
                                progressMonitor.setProgress(progressMonitor.getMaximum() - totalTasks);
                            }
                        }
                        if (progressMonitor.isCanceled()) {
                            taskList.stream().forEach((aFuture) -> aFuture.cancel(true));
                        }
                        if (progressMonitor.isCanceled()) {
                            break;
                        }
                        sleep(250);
                    }
                }
            }
            progressMonitor.setNote("cleaning up");
            executor.shutdown();
            progressMonitor.setProgress(progressMonitor.getMaximum());
            return true;
        }
    }

}
