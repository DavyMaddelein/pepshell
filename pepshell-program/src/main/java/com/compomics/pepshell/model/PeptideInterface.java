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
package com.compomics.pepshell.model;

/**
 *
 * @author Davy Maddelein
 */
public interface PeptideInterface {

    int getBeginningProteinMatch();

    int getEndProteinMatch();

    String getSequence();

    int getTimesFound();

    double getTotalSpectrumIntensity();

    void incrementTimesFound();

    boolean isIsMiscleaved();

    void setBeginningProteinMatch(int beginningProteinMatch);

    void setEndProteinMatch(int endProteinMatch);

    void setIsMiscleaved(boolean isMiscleaved);

    void setTimesFound(int timesFound);

    void setTotalSpectrumIntensity(double totalSpectrumIntensity);
    
}
