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

package com.compomics.pepshell.model.DataModes;

import com.compomics.pepshell.model.AnalysisGroup;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;

import javax.swing.*;
import java.util.*;
import java.awt.Color;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.sleep;

/**
 * Created by Davy Maddelein on 10/04/2015.
 */
public interface DataRetrievalInterface<T extends Experiment> {

    LinkedList<DataRetrievalStep> linkedSteps = new LinkedList<>();

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
    default T retrieveData(T referenceExperiment) {
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
    T retrievePrimaryData(T referenceExperiment);

    /**
     * retrieve any secondary data steps set for retrieval
     *
     * @param experiment the experiment to retrieve the secondary data for
     */
    default void retrieveSecondaryData(T experiment) {
        new runRetrievalSteps(experiment,getDataRetrievalSteps());
    }

    default void retrieveSecondaryData(T experiment,Observer observerToNotify){
        new runRetrievalSteps(experiment,getDataRetrievalSteps(),observerToNotify).execute();
    }

    default void setDataRetrievalSteps(LinkedList<DataRetrievalStep> retrievalStepsToSet) {
        linkedSteps.clear();
        linkedSteps.addAll(retrievalStepsToSet);
    }

    default Collection<DataRetrievalStep> getDataRetrievalSteps() {
        return Collections.unmodifiableCollection(linkedSteps);
    }
    //can be moved to separate class
    class runRetrievalSteps extends SwingWorker<Boolean, Void> {

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
        public runRetrievalSteps(Experiment anExperiment, Collection<DataRetrievalStep> retrievalSteps, Observer anObserver) {
            observer = anObserver;
            experiment = anExperiment;
            stepsToExecute = retrievalSteps;
        }

        public runRetrievalSteps(Experiment anExperiment,Collection<DataRetrievalStep> retrievalSteps) {
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
            List<List<PepshellProtein>> partitionedProteinList = new ArrayList<>(Runtime.getRuntime().availableProcessors() + 1);
            List<PepshellProtein> proteins = experiment.getProteins();
            for (int i = 0; i < proteins.size(); i += Math.floor(experiment.getProteins().size() / Runtime.getRuntime().availableProcessors())) {
                if ((i + experiment.getProteins().size() / Runtime.getRuntime().availableProcessors()) > proteins.size()) {
                    partitionedProteinList.add(proteins.subList(i, proteins.size()));
                } else {
                    partitionedProteinList.add(proteins.subList(i, i + (experiment.getProteins().size() / Runtime.getRuntime().availableProcessors())));
                }
            }

            int totalTasks = (partitionedProteinList.size() * stepsToExecute.size());

            progressMonitor.setMaximum(totalTasks + 2);

            for (DataRetrievalStep aStep : stepsToExecute) {
                List<Future<List<PepshellProtein>>> taskList = new ArrayList<>();
                partitionedProteinList.stream()
                        .map(aStep::getInstance)
                        .map((toExecute) -> {
                            toExecute.addObserver(observer);
                            return toExecute;
                        })
                        .forEach((toExecute) -> taskList.add(executor.submit(toExecute)));

                while (!taskList.isEmpty()) {
                    if (skipStep[0]) {
                        taskList.stream().forEach((aFuture) -> aFuture.cancel(true));
                        skipStep[0] = false;
                    } else {
                        Iterator<Future<java.util.List<PepshellProtein>>> futureIter = taskList.listIterator();
                        while (futureIter.hasNext()) {
                            Future<java.util.List<PepshellProtein>> aFuture = futureIter.next();
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

//    @Override
//    default void update(Observable o, Object arg) {
//        if (arg != null) {
//            if (arg instanceof UpdateMessage) {
//                //comes from lower, throw higher in the chain, update notification to user
//                this.setChanged();
//                progressMonitor.setNote(((UpdateMessage) arg).getMessage());
//                this.notifyObservers(arg);
//            }
//        }
//    }
}
