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
 * Created with IntelliJ IDEA.
 * User: Davy Maddelein
 * Date: 3/7/13
 * Time: 8:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class Domain {

   private String domainName;
    private int startPosition;
    private int stopPosition;
    private String website;

    public Domain(String domainName,int startPosition,int stopPosition,String website){
        this.domainName = domainName;
        this.startPosition = startPosition;
        this.stopPosition = stopPosition;
        this.website = website;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }

    public int getStartPosition() {
        return startPosition;
    }

    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    public int getStopPosition() {
        return stopPosition;
    }

    public void setStopPosition(int stopPosition) {
        this.stopPosition = stopPosition;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    
   @Override
    public String toString(){
        return domainName;
    }
}
