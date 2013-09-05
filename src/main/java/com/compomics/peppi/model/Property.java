package com.compomics.peppi.model;

/**
 *
 * @author Davy
 */
public final class Property {
    
    private String name;
    private String value;
    
    public Property(String aName, String aValue) {
        this.name = aName;
        this.value = aValue;
    }

    final public String getName() {
        return name;
    }

    final public String getValue() {
        return value;
    }
    
}
