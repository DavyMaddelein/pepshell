package com.compomics.pepshell.view.DrawModes.Peptides;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.ProgramVariables;
import com.compomics.pepshell.model.Protein;
import com.compomics.pepshell.model.QuantedPeptide;
import com.compomics.pepshell.model.exceptions.CalculationException;
import com.compomics.pepshell.model.exceptions.UndrawableException;
import com.compomics.pepshell.view.DrawModes.GradientDrawModeInterface;
import com.compomics.pepshell.view.DrawModes.StandardPeptideProteinDrawMode;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Davy
 * @param <U>
 */
public class QuantedPeptideDrawMode<U extends QuantedPeptide> extends StandardPeptideProteinDrawMode<Protein, U> implements GradientDrawModeInterface<Protein, U> {

    private RatioType topnumber = RatioType.LIGHT;
    private RatioType divisor = RatioType.HEAVY;
    private Double maxRatio = 0.0;

    @Override
    public void drawPeptide(U peptide, Graphics g, int horizontalOffset, int verticalOffset, int verticalBarSize) throws UndrawableException {
        try {
            g.setColor(calculatePeptideGradient(peptide));
        } catch (CalculationException ex) {
            FaultBarrier.getInstance().handleException(ex);
            throw new UndrawableException("could not calculate the ratio gradient");
        }
        super.drawPeptide(peptide, g, horizontalOffset, verticalOffset, verticalBarSize);
    }

    @Override
    public Color calculatePeptideGradient(U peptide) throws CalculationException {
        Color gradientColor = null;
        if (peptide.getRatio() == null || maxRatio == 0.0) {
            if (peptide.getHeavy().isEmpty() && peptide.getLight().isEmpty()) {
                throw new CalculationException("could not calculate ratio, missing light and/or heavy values");
            }
        }
        //if needed add medium here
        System.out.println(peptide.getRatio());
        gradientColor = new Color((int) Math.ceil(255 * (Math.log(peptide.getRatio())/Math.log(2) / (Math.log(maxRatio)/Math.log(2)))), (int) Math.ceil(255 / (Math.log(peptide.getRatio())/Math.log(2)) / (Math.log(maxRatio))), 255);
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
    public void drawColorLegend(int xOffset, int yOffset, Graphics g) {
        int colourCounter = 0;
        while (colourCounter < 51) {
            g.setColor(new Color(colourCounter * 5, 255 - (colourCounter * 5), 115));
            g.fillRect(xOffset + colourCounter, yOffset, 5, ProgramVariables.VERTICALSIZE);
            colourCounter += 5;
        }

        g.setColor(Color.black);
        g.drawString("low", xOffset, yOffset + 25);
        g.drawString("high", xOffset + colourCounter - 5, yOffset + 25);

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
