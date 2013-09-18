package com.compomics.peppi.controllers.DAO;

import com.compomics.peppi.model.DAS.DasFeature;
import com.compomics.peppi.model.DAS.DasMethod;

import java.util.ArrayList;
import java.util.List;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * Created with IntelliJ IDEA.
 * User: Davy
 * Date: 3/4/13
 * Time: 11:29 AM
 * To change this template use File | Settings | File Templates.
 */
public class DasParser {
    
    public static List<DasFeature> getAllDasFeatures (XMLEventReader dasXMLFile) throws XMLStreamException {
        XMLEvent dasEvent;
        List<DasFeature> allFeatures = new ArrayList<DasFeature>();
        while(dasXMLFile.hasNext()){
            if ((dasEvent = dasXMLFile.nextEvent()).isStartElement()){
                if(dasEvent.asStartElement().getName().getLocalPart().equalsIgnoreCase("FEATURE")){
                    dasEvent = dasXMLFile.nextEvent();
                    if(dasEvent.isCharacters()){
                        allFeatures.add(new DasFeature(dasEvent.asCharacters().getData()));
                    }
                }
            }
        }
    return allFeatures;
    }
    
    public static DasMethod getDasMethodForFeature(DasFeature feature) {
        return null;
    }
}
