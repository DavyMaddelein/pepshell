package com.compomics.pepshell.controllers.ViewPreparation;

import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AccessionConverting;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AddDomains;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.AddPdbInfo;
import com.compomics.pepshell.controllers.ViewPreparation.dataretrievalsteps.CPDTAnalysis;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Protein;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

/**
 *
 * @author Davy
 */
public abstract class ViewPreparation<T extends Experiment, V extends Protein> implements Observer {

    LinkedList<DataRetrievalStep> linkedSteps = new LinkedList<DataRetrievalStep>() {
        {
            //default list
            this.add(new AccessionConverting());
            this.add(new AddDomains());
            this.add(new AddPdbInfo());
            this.add(new CPDTAnalysis());
        }
    };

    protected ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    protected ProgressMonitor progressMonitor;
    protected boolean skipStep = false;

    public void start(T referenceExperiment, Iterator<T> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        JPanel progressMonitorPanel = new JPanel();
        progressMonitorPanel.setBackground(Color.white);
        progressMonitorPanel.setOpaque(true);
        JButton skipButton = new JButton();
        skipButton.setText("skip current retrieval step");
        skipButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                skipStep = true;
            }
        });
        progressMonitor = new ProgressMonitor(progressMonitorPanel, skipButton, "retrieving data", 0, 101);
        retrieveData(referenceExperiment, ExperimentsToCompareWith, removeNonOverlappingPeptidesFromReferenceProject);
    }

    protected abstract T retrieveData(T referenceExperiment, Iterator<T> ExperimentsToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject);

    protected abstract void retrieveSecondaryData(T experiment);

    protected abstract boolean checkAndAddQuantToProteinsInExperiment(T anExperiment);

    protected boolean removeProteinsNotInReferenceExperiment(T referenceProject, T projectToCompareWith, boolean removeNonOverlappingPeptidesFromReferenceProject) {
        //perhaps change with list so it is easier to notify observers of progress
        boolean finished = false;
        for (Protein aProtein : referenceProject.getProteins()) {
            if (projectToCompareWith.getProteins().contains(aProtein)) {
                aProtein.getProteinInfo().increaseNumberOfProjectOccurences();
                projectToCompareWith.getProteins().get(projectToCompareWith.getProteins().indexOf(aProtein)).getProteinInfo().increaseNumberOfProjectOccurences();
            }
        }
        if (removeNonOverlappingPeptidesFromReferenceProject) {
            //replace with iterator and remove
            for (Protein aProtein : projectToCompareWith.getProteins()) {
                if (aProtein.getProteinInfo().getNumberOfProjectOccurences() == 0) {

                }
            }
        }

        return finished;
    }

    public abstract void addProteinsToExperiment(AbstractDataMode dataMode);

    public void setDataRetievalSteps(LinkedList<DataRetrievalStep> linkedSteps) {
        this.linkedSteps.clear();
        this.linkedSteps.addAll(linkedSteps);
    }

    //can be moved to separate class
    protected class WaitForFutures extends SwingWorker<Boolean, Void> {

        private Observer observer;
        private Experiment experiment;

        public WaitForFutures(Experiment anExperiment, Observer anObserver) {
            observer = anObserver;
            experiment = anExperiment;
        }

        @Override
        protected Boolean doInBackground() throws Exception {
            //TODO add preliminary checks to see if the server is accessible, if not, skip and warn user       
            /**
             * the logic for splitting up the tasks and then splitting up the
             * list of proteins, is that this way skipping unwanted steps
             * becomes granular and we can still multi thread each step instead
             * of front loading the list and then terminating whatever steps are
             * left or filtering the step that has to be skipped out of the
             * executor queue
             *
             */
            List<List<Protein>> partitionedProteinList = Lists.partition(experiment.getProteins(), (int) Math.ceil(experiment.getProteins().size() / Runtime.getRuntime().availableProcessors()));
            for (DataRetrievalStep aStep : linkedSteps) {
                List<Future<List<Protein>>> taskList = new ArrayList<>();
                for (List<Protein> aProteinList : partitionedProteinList) {
                    DataRetrievalStep toExecute = aStep.getInstance(aProteinList);
                    toExecute.addObserver(observer);
                    taskList.add(executor.submit(toExecute));
                }
                //+2 because the partition returns the rest in an extra list, for example 4 can give 4 or 5 lists
                progressMonitor.setMaximum(taskList.size() + 2);

                while (!taskList.isEmpty()) {
                    if (skipStep) {
                        for (Future<List<Protein>> aFuture : taskList) {
                            aFuture.cancel(true);
                        }
                        skipStep = false;
                    } else {
                        Iterator<Future<List<Protein>>> futureIter = taskList.listIterator();
                        while (futureIter.hasNext()) {
                            Future<List<Protein>> aFuture = futureIter.next();
                            if (aFuture.isDone() || aFuture.isCancelled()) {
                                futureIter.remove();
                                progressMonitor.setProgress(progressMonitor.getMaximum() - taskList.size());
                            }
                        }
                        if (progressMonitor.isCanceled()) {
                            for (Future<List<Protein>> aFuture : taskList) {
                                aFuture.cancel(true);
                            }
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

    @Override
    public void update(Observable o, Object arg) {
        if (arg != null) {
            if (arg instanceof String) {
                progressMonitor.setNote((String) arg);
            }
        }
    }

}
