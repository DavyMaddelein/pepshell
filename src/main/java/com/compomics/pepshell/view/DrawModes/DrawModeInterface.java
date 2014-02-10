package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public interface DrawModeInterface<T extends Protein, U extends Peptide> {

    public void drawProtein(T protein, Graphics g, int horizontalOffset, int verticalOffset, int horizontalBarSize, int verticalBarWidth) throws UndrawableException;

    public void drawPeptide(U peptide, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarWidth) throws UndrawableException;
}
