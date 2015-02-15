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

package com.compomics.pepshell.view.statistics;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Davy Maddelein
 */
public class RatioStatisticsPane extends JFreeChartPanel {

    private List<Experiment> experimentGroup = new ArrayList<>();
    private Protein currentProtein;

    public <T extends Experiment> RatioStatisticsPane(List<T> experiments) {
        super();
        this.experimentGroup = (List<Experiment>) experiments;
        //chart.setMinimumDrawWidth(5000);
        //chart.setBorder(BorderFactory.createTitledBorder(experiment.getExperimentName()));
    }

    @Override
    public void setGraphData(Protein aProtein) {
        //obligatory checks
        if (aProtein != null) {
            if (aProtein != currentProtein) {
                //TODO: run this outside of the gui thread
                CategoryDataset dataset = createRatioDataset(aProtein);
                JFreeChart ratioChart = ChartFactory.createBarChart("log 2 ratios of peptides on a protein", aProtein.getVisibleAccession(), "log 2 ratio", dataset, PlotOrientation.VERTICAL, true, true, false);
                prettifyChart(ratioChart);
//((CategoryPlot)ratioChart.getPlot()).setRenderer(new StatisticalBarRenderer());
                chart.setChart(ratioChart);
            }
        } else {
            chart.setChart(null);
            this.getGraphics().drawString("missing data", 0, 0);
        }
    }

    private CategoryDataset createRatioDataset(Protein aProtein) {
        DefaultCategoryDataset returnset = new DefaultCategoryDataset();
        //Protein protein = experimentGroup.get(0).getProteins().get(experimentGroup.get(0).getProteins().indexOf(aProtein));
        if (aProtein != null) {
            for (Experiment anExperiment : experimentGroup) {
                if (anExperiment.getProteins().indexOf(aProtein) != -1) {
                    Protein protein = anExperiment.getProteins().get(anExperiment.getProteins().indexOf(aProtein));
                    Ordering<PeptideGroup> groupOrdering = Ordering.natural().onResultOf(getProteinLocation);
                    ImmutableList<PeptideGroup<PeptideInterface>> sortedCopy = groupOrdering.immutableSortedCopy(protein.getPeptideGroups());
                    for (PeptideGroup aPeptideGroup : sortedCopy) {
                        PeptideInterface aPeptide = aPeptideGroup.getShortestPeptide();
                        try {
                            if (aPeptide instanceof QuantedPeptide && ((QuantedPeptide) aPeptide).getRatio() != null) {
                                Double value = Math.log(((QuantedPeptide) aPeptide).getRatio()) / Math.log(2);
                                returnset.addValue(value, anExperiment.getExperimentName(), String.valueOf(aPeptide.getBeginningProteinMatch()));
                                //this part can be put in it's own method
                                for (Experiment checkList : experimentGroup) {
                                    if (!checkList.equals(anExperiment) && returnset.getRowKeys().contains(checkList.getExperimentName())) {
                                        if (returnset.getValue(checkList.getExperimentName(), String.valueOf(aPeptide.getBeginningProteinMatch())) == null) {
                                            returnset.addValue(null, checkList.getExperimentName(), String.valueOf(aPeptide.getBeginningProteinMatch()));
                                        }
                                    }
                                }
                            }
                        } catch (CalculationException ex) {
                            FaultBarrier.getInstance().handleException(ex);

                        }
                    }

                }
            }
        }
        return returnset;
    }

    private Function<PeptideGroup, Integer> getProteinLocation = new Function<PeptideGroup, Integer>() {
        @Override
        public Integer apply(PeptideGroup input) {
            return input.getShortestPeptide().getBeginningProteinMatch();
        }
    };
}
