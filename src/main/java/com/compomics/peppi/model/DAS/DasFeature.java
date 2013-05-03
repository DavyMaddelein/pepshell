package com.compomics.peppi.model.DAS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/4/13 Time: 11:31 AM To change
 * this template use File | Settings | File Templates.
 */
public class DasFeature {

    private List<String> features = new ArrayList<String>();
    private Set<String> secondaryStructures = new HashSet<String>();
    private String featureId;
    private String featureLabel;
    private String typeId;
    private String typeCategory;
    private String typeReference;
    private String typeSubparts;
    private String typeSuperparts;
    private String type;
    private String methodId;
    private String method;
    private int start;
    private int end;
    private double score;
    private String orientation;
    private String phase;
    private List<String> note = new ArrayList<String>();
    private List<Link> links = new ArrayList<Link>();
    private boolean valid = true;

    public DasFeature(String feature) {

        this.featureLabel = feature.substring(feature.indexOf("label=") + 7, feature.indexOf("\">"));
        if (featureLabel.equalsIgnoreCase("Invalid segment")) {
            valid = false;
            return;
        }
        this.featureId = feature.substring(feature.indexOf("id=") + 4, feature.indexOf("label") - 2);
        this.typeId = feature.substring(feature.indexOf("id=", feature.indexOf("<TYPE")) + 4, feature.indexOf("\"", feature.indexOf("\"", feature.indexOf("<TYPE")) + 1));
        if (feature.contains("category")) {
            this.typeCategory = feature.substring(feature.indexOf("category=") + 10, (feature.indexOf("\"", feature.indexOf("category=") + 10)));
        }
        if (feature.contains("reference")) {
            this.typeReference = feature.substring(feature.indexOf("reference=") + 11, (feature.indexOf("\"", feature.indexOf("reference=") + 11)));
        }
        if (feature.contains("subparts")) {
            this.typeSubparts = feature.substring(feature.indexOf("subparts=") + 10, (feature.indexOf("\"", feature.indexOf("subparts=") + 10)));
        }
        if (feature.contains("superparts")) {
            this.typeSuperparts = feature.substring(feature.indexOf("superparts=") + 12, (feature.indexOf("\"", feature.indexOf("superparts=") + 12)));
        }
        if (feature.contains("<TYPE") && !feature.contains("</TYPE")) {
            this.type = "no type";
        } else {
            this.type = feature.substring(feature.indexOf(">", feature.indexOf("<TYPE")) + 1, feature.indexOf("</TYPE"));
        }
        this.methodId = feature.substring(feature.indexOf("id=", feature.indexOf("<METHOD")) + 4, feature.indexOf("\">", feature.indexOf("<METHOD")));
        this.method = feature.substring(feature.indexOf(">", feature.indexOf("<METHOD")) + 1, feature.indexOf("</METHOD"));
        if (feature.contains("<START")) {
            this.start = new Integer(feature.substring(feature.indexOf(">", feature.indexOf("<START")) + 1, feature.indexOf("</START")));
        } else {
            this.start = -1;
        }
        if (feature.contains("<END")) {
            this.end = new Integer(feature.substring(feature.indexOf(">", feature.indexOf("<END")) + 1, feature.indexOf("</END")));
        } else {
            this.end = -2;
        }

        if (feature.contains("score")) {
            this.score = new Double(feature.substring(feature.indexOf(">", feature.indexOf("<SCORE")) + 1, feature.indexOf("</SCORE")));
        }

        if (feature.contains("<ORIENTATION")) {
            this.orientation = feature.substring(feature.indexOf(">", feature.indexOf("<ORIENTATION")) + 1, feature.indexOf("</ORIENTATION"));
        }
        if (feature.contains("<PHASE")) {
            this.phase = feature.substring(feature.indexOf(">", feature.indexOf("<PHASE")) + 1, feature.indexOf("</PHASE"));
        }
        if (feature.contains("<NOTE")) {
            int lastNoteFound = 0;
            while (feature.indexOf("<NOTE", lastNoteFound) != -1) {
                note.add(feature.substring(feature.indexOf(">", feature.indexOf("<NOTE", lastNoteFound)) + 1, feature.indexOf("</NOTE", lastNoteFound)));
                lastNoteFound = feature.indexOf("</NOTE", lastNoteFound) + 6;
            }

        }
        if (feature.contains("<LINK")) {
            int lastLinkFound = 0;
            while (feature.indexOf("<LINK", lastLinkFound) != -1) {
                links.add(new Link(feature.substring(feature.indexOf(">", feature.indexOf("<LINK", lastLinkFound)) + 1, feature.indexOf("</LINK", lastLinkFound)), feature.substring(feature.indexOf("href", feature.indexOf("<LINK", lastLinkFound)) + 6, feature.indexOf(">", feature.indexOf("<LINK", lastLinkFound)) - 1)));
                lastLinkFound = feature.indexOf("</LINK", lastLinkFound) + 6;
            }
        }

    }

    //getters
    public String getFeatureId() {
        return this.featureId;
    }

    public String getFeatureLabel() {
        return this.featureLabel;
    }

    public String getTypeId() {
        return this.typeId;
    }

    public String getTypeCategory() {
        return this.typeCategory;
    }

    public String getTypeReference() {
        return this.typeReference;
    }

    public String getTypeSubparts() {
        return this.typeSubparts;
    }

    public String getTypeSuperparts() {
        return this.typeSuperparts;
    }

    public String getType() {
        return this.type;
    }

    public String getMethodId() {
        return this.methodId;
    }

    public String getMethod() {
        return this.method;
    }

    public int getStart() {
        return this.start;
    }

    public int getEnd() {
        return this.end;
    }

    public double getScore() {
        return this.score;
    }

    public String getOrientation() {
        return this.orientation;
    }

    public String getPhase() {
        return this.phase;
    }

    public List<String> getNotes() {
        return this.note;
    }

    public List<Link> getLinks() {
        return this.links;
    }

    //toString method
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Feature id: ").append(featureId).append(" feature label: ").append(featureLabel);
        result.append("\nTypeid: ").append(typeId).append(" type catagory: ").append(typeCategory).append(" type reference: ");
        result.append(typeReference).append(" type subparts: ").append(typeSubparts).append(" type superparts: ").append(typeSuperparts);
        result.append(" type: ").append(type);
        result.append("\nMethodid: ").append(methodId).append(" method: ").append(method);
        result.append("\nStart: ").append(start);
        result.append("\nEnd: ").append(end);
        result.append("\nScore: ").append(score);
        result.append("\nOrientation: ").append(orientation);
        result.append("\nPhase: ").append(phase);
        if (!note.isEmpty()) {
            for (String aNote : note) {
                result.append("\nNote: ").append(aNote);
            }
        }
        if (!links.isEmpty()) {
            for (Link aLink : links) {
                result.append("\nLink: ").append(aLink.getLink()).append(" (").append(aLink.getHLink()).append(")");
            }
        }

        return result.toString();
    }

    public boolean isValid() {
        return valid;
    }

    public List<String> getFeatures() {
        return features;
    }

    public Set<String> getSecondaryStructuresFoundInDasFeatures() {
        return secondaryStructures;
    }

    public class Link {

        String link;
        String hLink;

        public Link(String aLink, String aHLink) {
            this.link = aLink;
            this.hLink = aHLink;

        }

        public String getLink() {
            return link;
        }

        public String getHLink() {
            return hLink;
        }
    }
}
