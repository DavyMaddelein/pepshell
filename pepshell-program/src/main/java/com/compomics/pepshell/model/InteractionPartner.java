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
public class InteractionPartner {

    private final int interactorLocation;
    private final int complementLocation;
    private final String interactionType;

    /**
     *
     * @param aResiduePartnerLocation
     * @param complementPartnerLocation
     * @param interactionType
     */
    public InteractionPartner(int aResiduePartnerLocation, int complementPartnerLocation, String interactionType) {
        this.interactorLocation = aResiduePartnerLocation;
        this.complementLocation = complementPartnerLocation;
        this.interactionType = interactionType;
    }

    public int getInteractorLocation() {
        return interactorLocation;
    }

    public int getComplementLocation() {
        return complementLocation;
    }

    public String getInteractionType() {
        return interactionType;
    }

}
