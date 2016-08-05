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

package com.compomics.pepshell.model.protein.proteininfo;

/**
 * Created by Davy Maddelein on 12/02/2015.
 */
public class FeatureWithLocation extends ProteinFeature {

    private int endPosition = -1;
    private int startPostion = -1;

    public FeatureWithLocation(String aDescription, int proteinLocation) {
        super(aDescription);
        startPostion = proteinLocation;
        endPosition = proteinLocation;
    }

    public FeatureWithLocation(String aDescription, int aStartPosition, int anEndPosition) {
        super(aDescription);
        startPostion = aStartPosition;
        endPosition = anEndPosition;
    }

    public int getEndPosition() {
        return endPosition;
    }

    public int getStartPosition() {
        return startPostion;
    }
}
