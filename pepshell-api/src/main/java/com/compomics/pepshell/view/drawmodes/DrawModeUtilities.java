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
package com.compomics.pepshell.view.drawmodes;

//import java.lang.InstantiationException;

import java.awt.*;

/**
 * this class only contains static methods that aid in the drawing of various
 * pepshell components and visualisations
 *
 * @author Davy Maddelein
 */
public class DrawModeUtilities {

    private static final DrawModeUtilities instance = new DrawModeUtilities();
    private SCALINGOPERATION scalingOperation = SCALINGOPERATION.LINEARSCALE;
    private static double SCALE = 1;


    /**
     * the alpha value to draw the protein at
     */
    private static float proteinAlpha = 1f;

    /**
     * the alpha value to draw the peptides at
     */
    private static float peptideAlpha = 1f;

    /**
     * the color the proteins should be displayed in
     */
    private static Color proteinColor = new Color(93, 100, 202);


    /**
     * the color the peptides should be displayed in
     */
    private static Color peptideColor = new Color(223, 156, 55);



    public static void setProteinAlpha(float alpha) {
        proteinAlpha = alpha;
    }


    public static void setPeptideAlpha(float alpha) {
        proteinAlpha = alpha;
    }


    public static void setProteinColor(Color aColor) {
        proteinColor = aColor;
    }


    public static void setPeptideColor(Color aColor) {
        peptideColor = aColor;
    }


    public static float getProteinAlpha() {
        return proteinAlpha;
    }


    public static float getPeptideAlpha() {
        return peptideAlpha;
    }


    public static Color getProteinColor() {
        return proteinColor;
    }


    public static Color getPeptideColor() {
        return peptideColor;
    }

    /**
     * should not be instantiated
     */
    private DrawModeUtilities() {
        //throw new InstantiationException("nuuuh uuuuh uuuuh, you didn't say the magic word");
    }

    public int scale(int distanceToScale) {
        return scalingOperation.currentStrategy.scale(distanceToScale);
    }

    public static DrawModeUtilities getInstance() {
        return instance;
    }

    public static double getScale() {
        return SCALE;
    }

    public static void setScale(double SCALE) {
        DrawModeUtilities.SCALE = SCALE;
    }

    public void setScalingStrategy(SCALINGOPERATION aScalingStrategy) {
        scalingOperation = aScalingStrategy;
    }

    public SCALINGOPERATION getCurrentScalingStrategy() {
        return scalingOperation;
    }

    public void zoom(Graphics2D g) {
        //((Graphics2D) g).translate(g.getWidth() / 2, this.getHeight() / 2);
        // ((Graphics2D) g).scale(1 / scale, 1 / scale);
        //this.setPreferredSize(new Dimension((int) Math.round(this.getWidth() * (1 / scale)), (int) Math.round(this.getHeight() * (1 / scale))));}
    }

    private interface scale {

        /**
         * scales the given distance to the scale set by setScale by
         *
         * @param distanceToScale the distance to be scaled (for example 100
         *                        pixels)
         * @return the scaled distance (rounded up if necessary)
         */
        public int scale(int distanceToScale);


    }

    public enum SCALINGOPERATION {

        LINEARSCALE(SCALINGSTRATEGY.MULTIPLYINGSCALE, SCALINGSTRATEGY.DIVIDINGSCALE), EXPONENTIALSCALE(SCALINGSTRATEGY.EXPONENTIALSCALE, SCALINGSTRATEGY.LOGSCALE);

        SCALINGSTRATEGY currentStrategy;
        SCALINGSTRATEGY reverseStrategy;

        private SCALINGOPERATION(SCALINGSTRATEGY aCurrentStrategy, SCALINGSTRATEGY aReverseStrategy) {
            currentStrategy = aCurrentStrategy;
            reverseStrategy = aReverseStrategy;
        }

        public SCALINGSTRATEGY flipStrategies() {
            SCALINGSTRATEGY strategeyHolder = currentStrategy;
            currentStrategy = reverseStrategy;
            reverseStrategy = strategeyHolder;
            return currentStrategy;
        }

        public SCALINGSTRATEGY getCurrentStrategy() {
            return currentStrategy;
        }

        public enum SCALINGSTRATEGY implements scale {

            MULTIPLYINGSCALE {

                @Override
                public int scale(int distanceToScale) {
                    int scaledSize = distanceToScale;
                    scaledSize = (int) (Math.ceil(scaledSize * DrawModeUtilities.getScale()));
                    return scaledSize;
                }

            },
            DIVIDINGSCALE {

                @Override
                public int scale(int distanceToScale) {
                    int scaledSize = distanceToScale;
                    scaledSize = (int) (Math.ceil(scaledSize / DrawModeUtilities.getScale()));
                    return scaledSize;
                }

            },
            EXPONENTIALSCALE {

                @Override
                public int scale(int distanceToScale) {
                    //to remove floating point shenanigans
                    return (int) Math.ceil(Math.pow(distanceToScale, DrawModeUtilities.getScale()));
                }


            }, LOGSCALE {

                @Override
                public int scale(int distanceToScale) {
                    //to remove floating point shenanigans
                    return (int) (Math.log(distanceToScale) / Math.log(DrawModeUtilities.getScale()) + 1e-10);
                }

            }
        }
    }
}
