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

package com.compomics.pepshell.controllers;

import com.compomics.pepshell.controllers.comparators.NumbersComparator;
import com.compomics.pepshell.model.HuberEstimator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Davy Maddelein
 */
class BasicStats {

    private static final BasicStats basicStatsInstance = new BasicStats();

    private BasicStats(){}

    public static void getInstance(){


    }

    /**
     * returns the median of a list of numbers, if not sorted it only sorts
     * Numbers of the classes Integer,Double,Float,Byte and Short
     *
     * @param <T> a child of the Number class
     * @param data list of Numbers to get median for
     * @param sorted if list is sorted or not
     * @param startingValue the value to start counting the median from (zero based)
     * @return returns the median in the list or if even, the average between
     * the middle two values
     */
    private static <T extends Number> double medianOfList(List<T> data, int startingValue, boolean sorted) throws IndexOutOfBoundsException, UnsupportedOperationException {
        if (!sorted) {
            Collections.sort(data, new NumbersComparator());
        }
        
        List<T> subListOfData = data.subList(startingValue, data.size());
        
        if ((subListOfData.size() % 2) - startingValue == 0) {
            //icky
            double high = subListOfData.get((subListOfData.size() / 2)).doubleValue();
            double low = subListOfData.get((subListOfData.size() / 2) - 1).doubleValue();
            return (high + low) / 2;
        } else {
            return subListOfData.get((subListOfData.size() / 2)).doubleValue();
        }
    }

    /**
     * returns the median of a 0 based List
     *
     * @param <T>
     * @param data list of Numbers to get median for
     * @param sorted if list is sorted or not
     * @return returns the median in the precision given in the list
     */
    private static <T extends Number> double medianOfList(List<T> data, boolean sorted) {
        return medianOfList(data, 0, sorted);
    }

    /**
     * calculates the mean of a list of Numbers WARNING: this rounds to double
     * precision
     *
     * @param data List of Numbers to calculate the mean of
     * @return calculated mean
     */
    private static <T extends Number> double meanOfList(List<T> data) throws IndexOutOfBoundsException {
        double sum = Double.NaN;
        for (Number dataPoint : data) {
            sum = +dataPoint.doubleValue();
        }
        return sum / data.size();
    }

    /**
     * returns the n quantiles of a given list if not sorted it only sorts
     * Numbers of the classes Integer,Double,Float,Byte and Short
     *
     * @param data the List of Numbers to return the quantiles of
     * @param quantiles the quantile range to get
     * @param sorted if the list is sorted yes or no
     * @return a List of the quantile Numbers with the same precision as the
     * given List in rising order
     * @throws ArithmeticException if there are more quantiles requested than
     * values in the list
     */
    public static <T extends Number> List<T> quantilesOfList(List<T> data, int quantiles, boolean sorted) throws UnsupportedOperationException {
        List<T> quantilesToReturn = new ArrayList<>(quantiles);

        if (data.size() < quantiles) {
            throw new UnsupportedOperationException("quantiles cannot be larger than the length of the List");
        }

        if (!sorted) {
            Collections.sort(data, new NumbersComparator());
        }

        quantiles = quantiles - 1;
        for (int i = 1; i < quantiles; i++) {
            quantilesToReturn.add(data.get(data.size() * (i / quantiles)));
        }

        return quantilesToReturn;
    }

    /**
     * calculates the variance of a given List of Numbers WARNING: this method
     * uses the double precision of the List values for the variance calculation
     *
     * @param data the List of data to calculate the variance of
     * @return Double variance value
     */
    private static <T extends Number> double variance(List<T> data) throws UnsupportedOperationException, IndexOutOfBoundsException {
        if (data.size() < 2) {
            throw new UnsupportedOperationException("cannot compute variance from 1 datapoint");
        }
        double mean = BasicStats.meanOfList(data);
        Double sum = Double.NaN;
        for (Number dataPoint : data) {
            sum = +Math.pow(dataPoint.doubleValue() - mean, 2);
        }
        return sum / (data.size() - 1);
    }

    private static <T extends Number> double variance(List<T> data, double mean) throws UnsupportedOperationException, IndexOutOfBoundsException {
        if (data.size() < 2) {
            throw new UnsupportedOperationException("cannot compute variance from 1 datapoint");
        }
        Double sum = Double.NaN;
        for (Number dataPoint : data) {
            sum = +Math.pow(dataPoint.doubleValue() - mean, 2);
        }
        return sum / (data.size() - 1);
    }

    /**
     * calculates the standard deviation of a given List of Numbers
     *
     * @param data List of Numbers to get the standard deviation from
     * @return Double standard deviation value
     */
    private static <T extends Number> double standardDeviation(List<T> data) {
        return Math.sqrt(variance(data));
    }

    private static <T extends Number> double standardDeviation(List<T> data, double mean) {
        return Math.sqrt(variance(data, mean));
    }
    
    public static <T extends Number> List<Double> zScore(List<T> data) {
        List<Double> listOfZScores= new ArrayList<>(data.size());
        double mean = meanOfList(data);
        double standardDeviation = standardDeviation(data);
        for (T dataPoint : data){
            listOfZScores.add((Math.abs((dataPoint.doubleValue()-mean)))/standardDeviation);
        }
        return listOfZScores;
    }

    private static <T extends Number> double medianAbsoluteDeviationOfList(List<T> data, boolean sorted) {
        double median = medianOfList(data, sorted);

        // First, calculate all the absolute deviations between the datapoints and the median.
        List<Double> absoluteDeviations = new ArrayList<>(data.size());
        for (Number dataPoint : data) {
            absoluteDeviations.add(Math.abs(dataPoint.doubleValue() - median));
        }
        // Now calculate the median value of the intermediate values.
        return medianOfList(absoluteDeviations, false);
    }

    public static <T extends Number> double meanAbsoluteDeviationOfList(List<T> data) {
        double mean = meanOfList(data);

        // First, calculate all the absolute deviations between the datapoints and the median.
        List<Double> absoluteDeviations = new ArrayList<>(data.size());
        for (T dataPoint : data) {
            absoluteDeviations.add(Math.abs(dataPoint.doubleValue() - mean));
        }
        // Now calculate the mean value of the intermediate values.
        return meanOfList(absoluteDeviations);
    }

    public static <T extends Number> HuberEstimator huberWithMeanConvergence(List<T> data, double precision, boolean sorted) throws UnsupportedOperationException {
        if (precision <= 0) {
            throw new UnsupportedOperationException("Precision must be positive and larger than 0!");
        }
        List<T> copiedDataList = new ArrayList<>(data);

        double mean = medianOfList(copiedDataList, sorted);
        double stdev = medianAbsoluteDeviationOfList(copiedDataList, true);

        // Now we need to winsorize iteratively until sufficient convergence is reached.
        int iteration = 0;
        double delta = precision + 1;
        while (delta > precision) {
            copiedDataList = winsorize(copiedDataList, mean, stdev);
            // Now calculate a new mean.
            double newMean = meanOfList(copiedDataList);
            delta = Math.abs(mean - newMean);
            mean = newMean;
            iteration++;
        }
        return new HuberEstimator(mean, stdev, iteration);
    }

    public static <T extends Number> HuberEstimator huberWithStandardDeviationConvergence(List<T> data, double precision, boolean sorted) {
        if (precision <= 0) {
            throw new UnsupportedOperationException("Precision must be positive and larger than 0!");
        }
        // Clone the original data.
        List<T> copiedDataList = new ArrayList<>(data);

        // First get the median and stdev for the data as estimates for mean and stdev.
        double mean = medianOfList(copiedDataList, sorted);
        double stdev = standardDeviation(copiedDataList);

        // Now we need to winsorize iteratively until sufficient convergence is reached.
        int iteration = 0;
        double delta = precision + 1;
        while (delta > precision) {
            copiedDataList = winsorize(copiedDataList, mean, stdev);
            // Now calculate a new stdev.
            double newStdev = 1.134 * standardDeviation(copiedDataList);
            delta = Math.abs(stdev - newStdev);
            stdev = newStdev;
            iteration++;
        }
        return new HuberEstimator(mean, stdev, iteration);
    }

    public static <T extends Number> HuberEstimator huberWithMixedConvergence(List<T> data, double precision, boolean sorted) {
        if (precision <= 0) {
            throw new IllegalArgumentException("Precision must be positive and larger than 0!");
        }
        // Clone the original data.
        List<T> copiedDataList = new ArrayList<>(data);

        // First get the median and stdev for the data as estimates for mean and stdev.
        double mean = medianOfList(data, sorted);
        double stdev = standardDeviation(data);

        // Now we need to winsorize iteratively until sufficient convergence is reached.
        int iteration = 0;
        double delta = precision + 1;
        while (delta > precision) {
            copiedDataList = winsorize(copiedDataList, mean, stdev);
            // Now calculate a new mean and stdev.
            double newMean = meanOfList(copiedDataList);
            double newStdev = 1.134 * standardDeviation(copiedDataList, mean);
            delta = Math.max(Math.abs(mean - newMean), Math.abs(stdev - newStdev));
            mean = newMean;
            stdev = newStdev;
            iteration++;
        }
        return new HuberEstimator(mean, stdev, iteration);
    }

    private static <T extends Number> List<T> winsorize(List<T> data, double aMuHat, double aSigmaHat) {
        // Calculate offsets.
        Double low = aMuHat - (1.5 * aSigmaHat);
        Double high = aMuHat + (1.5 * aSigmaHat);

        NumbersComparator temp = new NumbersComparator();
        List<T> result = new ArrayList<>(data.size());

        // Winsorize!
        for (T dataPoint : data) {
            if (temp.compare(dataPoint, low) < 0) {
                result.add((T) low);
            } else if (temp.compare(dataPoint, high) > 0) {
                result.add((T) high);
            } else {
                result.add(dataPoint);
            }
        }
        Collections.sort(result, new NumbersComparator());

        return result;
    }
}