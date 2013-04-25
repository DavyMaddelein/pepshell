package com.compomics.partialtryp.model.DAS.PDBDAS;

/**
 * Created with IntelliJ IDEA.
 * User: Davy
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
