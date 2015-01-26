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

package com.compomics.pepshell.model.DAS.PDBDAS;

/**
 * Created with IntelliJ IDEA.
 * User: Davy Maddelein
 * Date: 3/5/13
 * Time: 10:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class DasBlock {

    private int iPdbStart;
    private int iPdbEnd;
    private int iSpStart;
    private int iSpEnd;
    private String iPdbAccession;
    private String iSpAccession;

    public DasBlock(int aPdbStart, int aPdbEnd, int aSpStart, int aSpEnd, String aPdbAccession, String aSpAccession) {
        this.iPdbStart = aPdbStart;
        this.iPdbEnd = aPdbEnd;
        this.iSpStart = aSpStart;
        this.iSpEnd = aSpEnd;
        this.iPdbAccession = aPdbAccession;
        this.iSpAccession = aSpAccession;
    }

    //getters
    public int getPdbStart() {
        return this.iPdbStart;
    }

    public int getPdbEnd() {
        return this.iPdbEnd;
    }

    public int getSpStart() {
        return this.iSpStart;
    }

    public int getSpEnd() {
        return this.iSpEnd;
    }

    public String getPdbAccession() {
        return this.iPdbAccession;
    }

    public String getSpAccession() {
        return this.iSpAccession;
    }

    public int getDifference() {
        int diff = iSpStart - iPdbStart;
        return diff;
    }

}
