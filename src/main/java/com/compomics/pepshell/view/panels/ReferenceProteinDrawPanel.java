package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.PeptideGroup;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.DrawModeInterface;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.PdbGradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.Proteins.DomainProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.HydrophobicityProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Graphics;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author Niels Hulstaert
 */
public class ReferenceProteinDrawPanel extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(ReferenceProteinDrawPanel.class);
    private static final int HORIZONTAL_OFFSET = 15;
    private static final int VERTICAL_OFFSET = 15;

    /**
     * The reference protein to draw
     */
    private Protein protein;
    /**
     * The protein draw mode
     */
    private DrawModeInterface proteinDrawMode;
    /**
     * The peptide draw mode
     */
    private DrawModeInterface peptideDrawMode;
    /**
     * The secondary draw mode
     */
    private DrawModeInterface secondaryDrawMode;
    /**
     * The PDB accession
     */
    private String pdbAccession;
    /**
     * The domain draw mode
     */
    private DrawModeInterface domainDrawMode;

    /**
     * Constructor
     */
    public ReferenceProteinDrawPanel() {
        proteinDrawMode = new StandardPeptideProteinDrawMode();
        peptideDrawMode = new StandardPeptideProteinDrawMode();
        secondaryDrawMode = new HydrophobicityProteinDrawMode();
        domainDrawMode = new DomainProteinDrawMode();

        setOpaque(false);
    }

    /**
     * Update the protein to draw.
     *
     * @param protein
     */
    public void updateProtein(Protein protein) {
        this.protein = protein;
        this.revalidate();
        this.repaint();
    }

    /**
     * Update the secondary structure to draw.
     *
     * @param secondaryDrawMode
     * @param pdbAccession
     */
    public void updateSecondaryDrawMode(DrawModeInterface secondaryDrawMode, String pdbAccession) {
        this.secondaryDrawMode = secondaryDrawMode;
        this.pdbAccession = pdbAccession;
        this.revalidate();
        this.repaint();
    }

    /**
     * Update the peptide draw mode
     *
     * @param peptideDrawMode
     */
    public void updatePeptideDrawMode(DrawModeInterface peptideDrawMode) {
        this.peptideDrawMode = peptideDrawMode;
        this.revalidate();
        this.repaint();
    }

    /**
     * Update the PDB accession
     *
     * @param pdbAccession the PDB accession
     */
    public void updatePdbAccession(String pdbAccession) {
        if (!(pdbAccession == null || pdbAccession.equals(ReferenceExperimentPanel.NO_PDB_FILES_FOUND))) {
            this.pdbAccession = pdbAccession;
        } else {
            pdbAccession = null;
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (protein != null) {

            LOGGER.error("started painting");
            try {
                int scaledHorizontalBarSize = (int) Math.ceil(protein.getProteinSequence().length() * ProgramVariables.SCALE);

                proteinDrawMode.drawProtein(protein, g, HORIZONTAL_OFFSET, VERTICAL_OFFSET + 25, scaledHorizontalBarSize, ProgramVariables.VERTICALSIZE);
                for (PeptideGroup aGroup : protein.getPeptideGroupsForProtein()) {
                    peptideDrawMode.drawPeptide(aGroup.getShortestPeptide(), g, HORIZONTAL_OFFSET, VERTICAL_OFFSET + 25, ProgramVariables.VERTICALSIZE);
                }

                //pass the PDB accession if necessary
                if (secondaryDrawMode instanceof PdbGradientDrawModeInterface) {
                    ((PdbGradientDrawModeInterface) secondaryDrawMode).setPdbAccession(pdbAccession);
                }
                secondaryDrawMode.drawProtein(protein, g, HORIZONTAL_OFFSET, VERTICAL_OFFSET + 50, scaledHorizontalBarSize, ProgramVariables.VERTICALSIZE);
                if (secondaryDrawMode instanceof GradientDrawModeInterface) {
                    ((GradientDrawModeInterface) secondaryDrawMode).drawColorLegend(HORIZONTAL_OFFSET + scaledHorizontalBarSize + 15, VERTICAL_OFFSET + 50, g);
                }
                try {
                    if (protein.getDomains().isEmpty()) {
                        protein.addDomains(ProgramVariables.STRUCTUREDATASOURCE.getDomainData(protein));
                    }
                    domainDrawMode.drawProtein(protein, g, HORIZONTAL_OFFSET, VERTICAL_OFFSET, scaledHorizontalBarSize, ProgramVariables.VERTICALSIZE);
                } catch (DataRetrievalException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            } catch (UndrawableException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
            LOGGER.error("finished painting");
        }
    }

}
