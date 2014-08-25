/*
 * Here comes the text of your license
 * Each line should be prefixed with  * 
 */
package com.compomics.pepshell.view.panels.statistics;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import static com.compomics.pepshell.view.panels.statistics.JFreeChartPanel.prettifyChart;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSortedSet;
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
 * @author Davy
 */
public class RatioStatisticsPane extends JFreeChartPanel {

    private List<Experiment> experimentGroup = new ArrayList<>();
    private Protein currentProtein;

    public <T extends Experiment> RatioStatisticsPane(List<T> experiments) {
        super();
        this.experimentGroup = (List<Experiment>) experiments;
        //chart.setBorder(BorderFactory.createTitledBorder(experiment.getExperimentName()));
    }

    @Override
    public void setGraphData(Protein aProtein) {
        //obligatory checks
        if (aProtein != null) {
            if (aProtein != currentProtein) {
                //TODO: run this outside of the gui thread
                CategoryDataset dataset = createRatioDataset(aProtein);
                JFreeChart CPDTchart = ChartFactory.createBarChart("log ratios of peptides on a protein", aProtein.getProteinAccession(), "log ratio", dataset, PlotOrientation.VERTICAL, true, true, false);
                prettifyChart(CPDTchart);
                chart.setChart(CPDTchart);

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
            int experimentCounter = 0;
            for (Experiment anExperiment : experimentGroup) {
                experimentCounter++;

                if (anExperiment.getProteins().indexOf(aProtein) != -1) {
                    Protein protein = anExperiment.getProteins().get(anExperiment.getProteins().indexOf(aProtein));
                    Ordering<PeptideGroup> groupOrdering = Ordering.natural().onResultOf(getProteinLocation);
                    ImmutableList<PeptideGroup> sortedCopy = groupOrdering.immutableSortedCopy(protein.getPeptideGroupsForProtein());
                    for (PeptideGroup aPeptideGroup : sortedCopy) {
                        Peptide aPeptide = aPeptideGroup.getShortestPeptide();
                        try {
                            if (aPeptide instanceof QuantedPeptide && ((QuantedPeptide) aPeptide).getRatio() != null) {
                                Double value = Math.log(((QuantedPeptide) aPeptide).getRatio()) / Math.log(2);
                                returnset.addValue(value, (Integer) aPeptide.getBeginningProteinMatch(), (Integer) experimentCounter);
                                //returnset.addValue(((QuantedPeptide) aPeptide).getRatio(), String.valueOf(aPeptide.getBeginningProteinMatch()), String.valueOf(groupcounter));
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

    Function<PeptideGroup, Integer> getProteinLocation = new Function<PeptideGroup, Integer>() {
        @Override
        public Integer apply(PeptideGroup input) {
            return input.getShortestPeptide().getBeginningProteinMatch();
        }
    };

}
