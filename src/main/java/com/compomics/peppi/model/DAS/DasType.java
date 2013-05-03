/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.peppi.model.DAS;

/**
 *
 * @author Davy
 */
public class DasType {
    private String id;
    private String typeMessage;
    
    public DasType(String id, String typeMessage){
        this.id = id;
        this.typeMessage = typeMessage;
    }

    public String getTypId() {
        return id;
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}
