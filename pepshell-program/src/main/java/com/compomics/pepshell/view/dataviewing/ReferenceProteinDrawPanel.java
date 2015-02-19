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

package com.compomics.pepshell.view.dataviewing;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.protein.proteinimplementations.PepshellProtein;
import com.compomics.pepshell.model.protein.proteininfo.PdbInfo;
import com.compomics.pepshell.model.protein.ProteinInterface;
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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JPanel;

/**
 *
 * @author Niels Hulstaert
 */
public class ReferenceProteinDrawPanel extends JPanel {

    private static final int HORIZONTAL_OFFSET = 15;
    private static final int VERTICAL_OFFSET = 15;

    /**
     * The reference pepshellProtein to draw
     */
    private PepshellProtein pepshellProtein;
    /**
     * The pepshellProtein draw mode
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
        this.setBackground(Color.WHITE);
        setOpaque(false);
    }

    public ProteinInterface getPepshellProtein() {
        return pepshellProtein;
    }

    /**
     * Update the pepshellProtein to draw.
     *
     * @param pepshellProtein
     */
    public void updateProtein(PepshellProtein pepshellProtein) {
        this.pepshellProtein = pepshellProtein;

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
        if ((pdbInfo != null && pepshellProtein != null) && pepshellProtein.getPdbFilesInfo().contains(pdbInfo) && pepshellProtein instanceof PepshellProtein) {
            ((PepshellProtein) pepshellProtein).setPreferredPdfFile(pdbInfo);
            this.pdbAccession = pdbInfo;
        } else {
            pdbInfo = null;
        }
        this.revalidate();
        this.repaint();
    }

    @Override
    public Dimension getPreferredSize() {
        if (pepshellProtein != null) {
            return new Dimension(DrawModeUtilities.getInstance().scale(pepshellProtein.getProteinSequence().length()) + 150, this.getHeight());
        } else {
            return super.getPreferredSize();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (pepshellProtein != null) {
            try {
                //draw domains

                int width = DrawModeUtilities.getInstance().scale(pepshellProtein.getProteinSequence().length());
                Point offsetPoint = new Point(HORIZONTAL_OFFSET, VERTICAL_OFFSET);
                //pretty much move this entire try block to a separate thread
                if (pepshellProtein.getDomains().isEmpty()) {
                    new Runnable() {

                        @Override
                        public void run() {
                            try {
                                pepshellProtein.addDomains(ProgramVariables.STRUCTUREDATASOURCE.getDomainData(pepshellProtein));
                                repaint();
                            } catch (DataRetrievalException e) {
                                FaultBarrier.getInstance().handleException(e);
                            }
                        }
                    }.run();
                }
                domainDrawMode.drawProteinAndPeptides(pepshellProtein, g, offsetPoint, width, ProgramVariables.VERTICALSIZE);
                domainBackgroundDrawMode.setProteinAlpha(0.18f);
                domainBackgroundDrawMode.drawProteinAndPeptides(pepshellProtein, g, offsetPoint, width, this.getHeight());
                ((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

                offsetPoint.y += 25;
                proteinDrawMode.drawProteinAndPeptides(pepshellProtein, g, offsetPoint, width, ProgramVariables.VERTICALSIZE);

                offsetPoint.y += 25;
                secondaryDrawMode.drawProteinAndPeptides(pepshellProtein, g, offsetPoint, width, ProgramVariables.VERTICALSIZE);
                if (secondaryDrawMode instanceof GradientDrawModeInterface) {
                    ((GradientDrawModeInterface) secondaryDrawMode).drawColorLegend(new Point(offsetPoint.x + width + 15, offsetPoint.y), g);
                }
                offsetPoint.y += 25;
                try {
                    g.drawString("CPDT", offsetPoint.x + width + 15, offsetPoint.y + 87);
                    cpdtDrawMode.drawProteinAndPeptides(pepshellProtein, g, offsetPoint, width, ProgramVariables.VERTICALSIZE);
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
