package com.compomics.peppi.model.DAS.PDBDAS;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Davy
 * Date: 3/5/13
 * Time: 9:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class DasAlignment {

    //feature elements
    private String resolution = "";
    private String experiment_type = "";
    private String header = "";
    private String title = "";
    private String pdbAccession = "";
    private String spAccession = "";
    private List<DasBlock> alignmentBlocks = new ArrayList<DasBlock>();
    private String pdbGroup = "";

    public DasAlignment(String aDasAlignment) {
            this.experiment_type = aDasAlignment.substring(aDasAlignment.indexOf("property=\"method\">") + 18, aDasAlignment.indexOf("<", aDasAlignment.indexOf("property=\"method\">")));
            this.title = aDasAlignment.substring(aDasAlignment.indexOf("property=\"title\">") + 17, aDasAlignment.indexOf("<", aDasAlignment.indexOf("property=\"title\">")));
            this.pdbAccession = aDasAlignment.substring(aDasAlignment.indexOf("<alignObject dbAccessionId=\"") + 28, aDasAlignment.indexOf("\"", aDasAlignment.indexOf("<alignObject dbAccessionId=\"") + 28));
            this.spAccession = aDasAlignment.substring(aDasAlignment.indexOf("<alignObject dbAccessionId=\"", aDasAlignment.indexOf("<alignObject dbAccessionId=\"") + 28) + 28, aDasAlignment.indexOf("\"", aDasAlignment.indexOf("<alignObject dbAccessionId=\"", aDasAlignment.indexOf("<alignObject dbAccessionId=\"") + 28) + 28));
    }
    //getters

    public String getResolution() {
        return resolution;
    }

    public String getExperiment_type() {
        return experiment_type;
    }

    public String getHeader() {
        return header;
    }

    public String getTitle() {
        return title;
    }

    public String getPdbAccession() {
        return pdbAccession;
    }

    public String getSpAccession() {
        return spAccession;
    }

    public List<DasBlock> getAlignmentBlocks() {
        return alignmentBlocks;
    }

    public String getPdbGroup() {
        pdbGroup = pdbAccession.substring(pdbAccession.indexOf(".") + 1);
        pdbGroup = pdbGroup.toLowerCase(Locale.getDefault());
        return pdbGroup;
    }

}
