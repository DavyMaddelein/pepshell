package com.compomics.pepshell.view.panels.statistics;

import com.compomics.pepshell.model.CPDTPeptide;
import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Davy
 */
public class CleavingProbabilityPane extends JFreeChartPanel {

    private double cutoff = 0.7;
    private Protein currentProtein;
    private Experiment experiment;

    /**
     *
     * inherits javadoc
     */
    public <T extends Experiment> CleavingProbabilityPane(T anExperiment) {
        super();
        this.experiment = anExperiment;
        chart.setBorder(BorderFactory.createTitledBorder(experiment.getExperimentName()));
    }

    @Override
    public void setGraphData(Protein aProtein) {
        //obligatory checks
        if (aProtein != null && experiment.getProteins().contains(aProtein)) {
            if (aProtein != currentProtein) {
                //TODO: run this outside of the gui thread
                currentProtein = experiment.getProteins().get(experiment.getProteins().indexOf(aProtein));
                XYSeriesCollection xYSeriesCollection = new XYSeriesCollection();
                xYSeriesCollection.addSeries(fillSeries(currentProtein));
                JFreeChart CPDTchart = ChartFactory.createScatterPlot("probability of cleaving", currentProtein.getProteinAccession(), "probability", xYSeriesCollection, PlotOrientation.VERTICAL, false, true, false);
                prettifyChart(CPDTchart);
                chart.setChart(CPDTchart);
            }
        } else {
            chart.setChart(null);
            this.getGraphics().drawString("missing data", 0, 0);
        }
    }

    public void setCutoff(double aCutoff) {
        if (this.cutoff != aCutoff) {
            this.cutoff = aCutoff;
            setGraphData(currentProtein);
        }

    }

    private XYSeries fillSeries(Protein currentProtein) {
        XYSeries set = new XYSeries("");
        for (PeptideGroup aPeptideGroup : currentProtein.getCPDTPeptideGroups()) {
            if (aPeptideGroup.getShortestPeptide() instanceof CPDTPeptide) {
                if (((CPDTPeptide) aPeptideGroup.getShortestPeptide()).getProbability() > cutoff && aPeptideGroup.getShortestPeptide().getSequence().length() > 4) {
                    set.add(aPeptideGroup.getShortestPeptide().getBeginningProteinMatch(), ((CPDTPeptide) aPeptideGroup.getShortestPeptide()).getProbability());
                }
            }
        }
        return set;
    }

}
