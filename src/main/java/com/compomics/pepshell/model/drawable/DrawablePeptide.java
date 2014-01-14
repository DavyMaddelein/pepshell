package com.compomics.pepshell.model.drawable;

import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Peptide;
import com.compomics.pepshell.view.DrawModes.Drawable;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public class DrawablePeptide extends Peptide implements Drawable {
    /**
     * creates an instance of a drawable peptide 
     * @param sequence the peptide sequence
     */
    public DrawablePeptide(String sequence) {
        super(sequence);
    }

    /**
     * {@inheritDoc }
     */
    public void draw(int x, int y, Graphics g) {
        g.setColor(ProgramVariables.PEPTIDECOLOR);
        int startingLocation = (int) Math.ceil(((double) (this.getBeginningProteinMatch()) / ProgramVariables.SCALE));
        int scaledHorizontalSize = (int) Math.ceil((((double) (this.getEndProteinMatch() - this.getBeginningProteinMatch())) / ProgramVariables.SCALE));
        g.drawRect(x + startingLocation, y, scaledHorizontalSize, ProgramVariables.VERTICALSIZE);
    }
}
