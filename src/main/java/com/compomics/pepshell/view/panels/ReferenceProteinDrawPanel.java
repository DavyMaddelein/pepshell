package com.compomics.pepshell.view.panels;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.PdbInfo;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.ProteinInterface;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.DrawProteinPeptidesInterface;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.Proteins.CPDTCleavedProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.DomainProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.Proteins.HydrophobicityProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import com.compomics.pepshell.view.DrawModes.DrawModeUtilities;
import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;
import org.apache.log4j.Logger;

/**
 *
 * @author Niels Hulstaert
 */
class ReferenceProteinDrawPanel extends JPanel {

    private static final Logger LOGGER = Logger.getLogger(ReferenceProteinDrawPanel.class);
    private static final int HORIZONTAL_OFFSET = 15;
    private static final int VERTICAL_OFFSET = 15;

    /**
     * The reference protein to draw
     */
    private ProteinInterface protein;
    /**
     * The protein draw mode
     */
    private DrawProteinPeptidesInterface proteinDrawMode;

    /**
     * the domain draw mode
     */
    private final DomainProteinDrawMode domainBackgroundDrawMode;
    /**
     * The secondary draw mode
     */
    private DrawProteinPeptidesInterface secondaryDrawMode;
    /**
     * The PDB info object
     */
    private PdbInfo pdbAccession;
    /**
     * The domain draw mode
     */
    private final DrawProteinPeptidesInterface domainDrawMode;

    /**
     * the CPDT cleavage algorithm draw mode
     */
    private final DrawProteinPeptidesInterface cpdtDrawMode;

    /**
     * Constructor
     */
    public ReferenceProteinDrawPanel() {
        proteinDrawMode = new AbstractPeptideProteinDrawMode();
        secondaryDrawMode = new HydrophobicityProteinDrawMode();
        domainDrawMode = new DomainProteinDrawMode();
        cpdtDrawMode = new CPDTCleavedProteinDrawMode();
        domainBackgroundDrawMode = new DomainProteinDrawMode();

        setOpaque(false);
    }

    public ProteinInterface getProtein() {
        return protein;
    }

    /**
     * Update the protein to draw.
     *
     * @param protein
     */
    public void updateProtein(ProteinInterface protein) {
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
    public void updateSecondaryDrawMode(DrawProteinPeptidesInterface secondaryDrawMode, PdbInfo pdbAccession) {
        this.secondaryDrawMode = secondaryDrawMode;
        this.pdbAccession = pdbAccession;
        this.revalidate();
        this.repaint();
    }

    /**
     * Update the PDB accession
     *
     * @param pdbInfo the PDB accession
     */
    public void updatePdbInfo(PdbInfo pdbInfo) {
        if ((pdbInfo != null && protein != null) && protein.getPdbFilesInfo().contains(pdbInfo) && protein instanceof Protein) {
            ((Protein) protein).setPreferedPdbFile(pdbInfo);
            this.pdbAccession = pdbInfo;
        } else {
            pdbInfo = null;
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        if (protein != null) {
            return new Dimension(DrawModeUtilities.getInstance().scale(protein.getProteinSequence().length()) + 150, this.getHeight());
        } else {
            return super.getPreferredSize();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (protein != null) {
            try {
                //draw domains

                int width = DrawModeUtilities.getInstance().scale(protein.getProteinSequence().length());
                Point offsetPoint = new Point(HORIZONTAL_OFFSET, VERTICAL_OFFSET);
                //pretty much move this entire try block to a separate thread
                if (protein.getDomains().isEmpty()) {
                    new Runnable() {

                        @Override
                        public void run() {
                            try {
                                protein.addDomains(ProgramVariables.STRUCTUREDATASOURCE.getDomainData(protein));
                                repaint();
                            } catch (DataRetrievalException e) {
                                FaultBarrier.getInstance().handleException(e);
                            }
                        }
                    }.run();
                }
                domainDrawMode.drawProteinAndPeptides(protein, g, offsetPoint, width, ProgramVariables.VERTICALSIZE);
                domainBackgroundDrawMode.setProteinAlpha(0.18f);
                domainBackgroundDrawMode.drawProteinAndPeptides(protein, g, offsetPoint, width, this.getHeight());
                ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

                offsetPoint.y += 25;
                proteinDrawMode.drawProteinAndPeptides(protein, g, offsetPoint, width, ProgramVariables.VERTICALSIZE);

                offsetPoint.y += 25;
                secondaryDrawMode.drawProteinAndPeptides(protein, g, offsetPoint, width, ProgramVariables.VERTICALSIZE);
                if (secondaryDrawMode instanceof GradientDrawModeInterface) {
                    ((GradientDrawModeInterface) secondaryDrawMode).drawColorLegend(new Point(offsetPoint.x + width + 15, offsetPoint.y), g);
                }
                offsetPoint.y += 25;
                try {
                    g.drawString("CPDT", offsetPoint.x + width + 15, offsetPoint.y + 87);
                    cpdtDrawMode.drawProteinAndPeptides(protein, g, offsetPoint, width, ProgramVariables.VERTICALSIZE);
                } catch (UndrawableException ex) {
                    FaultBarrier.getInstance().handleException(ex);
                }
            } catch (UndrawableException ex) {
                FaultBarrier.getInstance().handleException(ex);
            }
        }
    }

    void updateMainProteinDrawMode(DrawProteinPeptidesInterface aProteinDrawMode) {
        proteinDrawMode = aProteinDrawMode;
        this.repaint();
    }
}
