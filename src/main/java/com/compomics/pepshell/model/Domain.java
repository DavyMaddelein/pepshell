package com.compomics.pepshell.model;

/**
 * Created with IntelliJ IDEA.
 * User: Davy
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
