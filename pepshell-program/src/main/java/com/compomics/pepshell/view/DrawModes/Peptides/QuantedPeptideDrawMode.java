package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.AbstractPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author Davy Maddelein
 * @param <U>
 */
public class QuantedPeptideDrawMode<U extends QuantedPeptide> extends AbstractPeptideProteinDrawMode<Protein, U> implements GradientDrawModeInterface<Protein, U> {

    private RatioType topnumber = RatioType.LIGHT;
    private RatioType divisor = RatioType.HEAVY;
    private Double maxRatio = 0.0;

    @Override
    public void drawPeptide(U peptide, Graphics g, Point startPoint, int length, int height) throws UndrawableException {
        try {
            g.setColor(calculatePeptideGradient(peptide));
        } catch (CalculationException ex) {
            UndrawableException updatedEx = new UndrawableException("could not calculate the ratio gradient");
            updatedEx.initCause(ex);
            throw updatedEx;
        }
        super.drawPeptide(peptide, g, startPoint, length, height);
    }

    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        Color gradientColor = null;
        if (peptide.getRatio() == null || maxRatio == 0.0) {
            if (peptide.getHeavy().isEmpty() && peptide.getLight().isEmpty()) {
                throw new CalculationException("could not calculate ratio, missing light and/or heavy values");
            } else {
            }
        }
        //if needed add medium here
        System.out.println(peptide.getRatio());
        try {
            gradientColor = new Color((int) Math.ceil(255 * (Math.log(peptide.getRatio()) / Math.log(2) / (Math.log(maxRatio) / Math.log(2)))), (int) Math.ceil(255 / (Math.log(peptide.getRatio()) / Math.log(2)) / (Math.log(maxRatio))), 255);
        } catch (IllegalArgumentException iae) {
            FaultBarrier.getInstance().handleException(iae);
        }
        return gradientColor;

    }

    public void setTopNumber(RatioType aType) {
        this.topnumber = aType;
    }

    public void setDivisor(RatioType aType) {
        this.divisor = aType;
    }

    @Override
    public Color calculateAminoAcidGradient(Protein protein, int location) throws CalculationException {
        return ProgramVariables.PROTEINCOLOR;
    }

    @Override
    public void drawColorLegend(Point legendStartPoint, Graphics g) {
        int colourCounter = 0;
        while (colourCounter < 51) {
            g.setColor(new Color(colourCounter * 5, 255 - (colourCounter * 5), 115));
            g.fillRect(legendStartPoint.x + colourCounter, legendStartPoint.y, 5, ProgramVariables.VERTICALSIZE);
            colourCounter += 5;
        }

        g.setColor(Color.black);
        g.drawString("low", legendStartPoint.x, legendStartPoint.y + 25);
        g.drawString("high", legendStartPoint.x + colourCounter - 5, legendStartPoint.y + 25);

    }

    public void setMaxRatio(Double maxRatio) {
        this.maxRatio = maxRatio;
    }

    public enum RatioType {

        LIGHT("light", "L"),
        HEAVY("heavy", "H");

        String type;
        String shorthand;

        RatioType(String type, String shorthand) {
            this.type = type;
            this.shorthand = shorthand;
        }

        public String getType() {
            return type;
        }

        public String getShorthand() {
            return shorthand;
        }
    }
}