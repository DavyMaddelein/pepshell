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
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.PeptideInterface;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Davy Maddelein
 */
public class RatioComparisonPane extends JFreeChartPanel {

    private final Experiment referenceExperiment;
    private Experiment experimentToCompareTo;

    public RatioComparisonPane(Experiment aReferenceExperiment) {
        referenceExperiment = aReferenceExperiment;
    }

    public RatioComparisonPane(Experiment aReferenceExperiment, Experiment aExperimentToCompareTo) {
        referenceExperiment = aReferenceExperiment;
        experimentToCompareTo = aExperimentToCompareTo;
    }

    @Override
    public void setGraphData(PepshellProtein aPepshellProtein) {
        if (aPepshellProtein != null && referenceExperiment.getProteins().contains(aPepshellProtein) && experimentToCompareTo.getProteins().contains(aPepshellProtein)) {
            CategoryDataset dataset = createRatioDataset(aPepshellProtein);
            JFreeChart ratioChart = ChartFactory.createBarChart("log ratios of peptides on a protein", aPepshellProtein.getVisibleAccession(), "log^2 ratio", dataset, PlotOrientation.VERTICAL, true, true, false);
            prettifyChart(ratioChart);
            chart.setChart(ratioChart);
        }

    }

    private CategoryDataset createRatioDataset(PepshellProtein aPepshellProtein) {
        DefaultCategoryDataset returnset = new DefaultCategoryDataset();
        PepshellProtein pepshellProtein = referenceExperiment.getProteins().get(referenceExperiment.getProteins().indexOf(aPepshellProtein));
        Ordering<PeptideGroup> groupOrdering = Ordering.natural().onResultOf(getProteinLocation);
        ImmutableList<? extends PeptideGroup> sortedCopy = groupOrdering.immutableSortedCopy(pepshellProtein.getPeptideGroups());
        for (PeptideGroup aPeptideGroup : sortedCopy) {
            PeptideInterface aPeptide = aPeptideGroup.getShortestPeptide();
            try {
                if (aPeptide instanceof QuantedPeptide && ((QuantedPeptide) aPeptide).getRatio() != null) {
                    Double value = Math.log(((QuantedPeptide) aPeptide).getRatio()) / Math.log(2);
                    returnset.addValue(value,(Integer) 1, (Integer) aPeptide.getBeginningProteinMatch());
                    //returnset.addValue(((QuantedPeptide) aPeptide).getRatio(), String.valueOf(aPeptide.getBeginningProteinMatch()), String.valueOf(groupcounter));
                }
            } catch (CalculationException ex) {
                FaultBarrier.getInstance().handleException(ex);

            }
        }
        pepshellProtein = referenceExperiment.getProteins().get(referenceExperiment.getProteins().indexOf(aPepshellProtein));
        groupOrdering = Ordering.natural().onResultOf(getProteinLocation);
        sortedCopy = groupOrdering.immutableSortedCopy(pepshellProtein.getPeptideGroups());
        for (PeptideGroup aPeptideGroup : sortedCopy) {
            PeptideInterface aPeptide = aPeptideGroup.getShortestPeptide();
            try {
                if (aPeptide instanceof QuantedPeptide && ((QuantedPeptide) aPeptide).getRatio() != null) {
                    Double value = Math.log(((QuantedPeptide) aPeptide).getRatio()) / Math.log(2);
                    returnset.addValue(value, (Integer) aPeptide.getBeginningProteinMatch(), (Integer) 2);
                    //returnset.addValue(((QuantedPeptide) aPeptide).getRatio(), String.valueOf(aPeptide.getBeginningProteinMatch()), String.valueOf(groupcounter));
                }
            } catch (CalculationException ex) {
                FaultBarrier.getInstance().handleException(ex);
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

    public void setComparisonExperiment(Experiment anExperiment) {
        experimentToCompareTo = anExperiment;
    }
}
