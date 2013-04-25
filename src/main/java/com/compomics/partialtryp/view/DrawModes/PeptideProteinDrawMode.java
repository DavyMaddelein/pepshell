/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.partialtryp.view.DrawModes;

import com.compomics.partialtryp.model.PeptideGroup;
import com.compomics.partialtryp.model.Protein;
import java.awt.Graphics;
import java.util.List;

/**
 *
 * @author Davy
 */
public interface PeptideProteinDrawMode {

    public void drawProtein(Protein protein, Graphics g, int horizontalOffset, int verticalOffset, int size, int width);

    public void drawPeptides(List<PeptideGroup> peptideGroups, Graphics g, int horizontalOffset, int verticalOffset, int size, int width);

    public void drawPeptide(PeptideGroup peptideGroup, Graphics g, int horizontalOffset, int verticalOffset, int size, int width,int barSize, int colourScale);
}
