package com.compomics.pepshell.model;

/**
 *
 * @author Davy
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
