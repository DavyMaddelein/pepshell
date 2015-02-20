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
package com.compomics.pepshell.view.DrawModes;

//import java.lang.InstantiationException;

import java.awt.Graphics2D;

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
     * should not be instanciated
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

    public static double getSCALE() {
        return SCALE;
    }

    public static void setSCALE(double SCALE) {
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
                    scaledSize = (int) (Math.ceil(scaledSize * DrawModeUtilities.getSCALE()));
                    return scaledSize;
                }

            },
            DIVIDINGSCALE {

                @Override
                public int scale(int distanceToScale) {
                    int scaledSize = distanceToScale;
                    scaledSize = (int) (Math.ceil(scaledSize / DrawModeUtilities.getSCALE()));
                    return scaledSize;
                }

            },
            EXPONENTIALSCALE {

                @Override
                public int scale(int distanceToScale) {
                    //to remove floating point schenanigans
                    return (int) Math.ceil(Math.pow(distanceToScale, DrawModeUtilities.getSCALE()));
                }


            }, LOGSCALE {

                @Override
                public int scale(int distanceToScale) {
                    //to remove floating point schenanigans
                    return (int) (Math.log(distanceToScale) / Math.log(DrawModeUtilities.getSCALE()) + 1e-10);
                }

            }
        }
    }
}
