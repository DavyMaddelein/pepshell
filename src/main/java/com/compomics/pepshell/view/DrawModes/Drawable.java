package com.compomics.pepshell.view.DrawModes;

import com.compomics.pepshell.model.exceptions.UndrawableException;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Davy
 */
public interface Drawable {

    //todo make it so that these are not final


    /**
     * draws the drawable object
     *
     * @param x the x coordinate to start from
     * @param y the y coordinate to start from
     * @param g the graphics to draw on
     */
    public void draw(int x, int y, Graphics g) throws UndrawableException;
}
