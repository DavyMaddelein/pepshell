package com.compomics.peppi.controllers.secondarystructureprediction;

import com.compomics.peppi.controllers.DAO.DasParser;
import com.compomics.peppi.controllers.DAO.URLController;
import static com.compomics.peppi.controllers.secondarystructureprediction.SecondaryStructurePrediction.secStructMap;
import com.compomics.peppi.controllers.comparators.CompareDasFeatures;
import com.compomics.peppi.model.DAS.DasFeature;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/6/13 Time: 1:11 PM To change
 * this template use File | Settings | File Templates.
 */
public class UniprotSecondaryStructurePrediction extends SecondaryStructurePrediction {

    @Override
    public String getPrediction(String anUniprotAccession) throws IOException {

        StringBuilder predictionResult = new StringBuilder();
        XMLInputFactory xmlParseFactory = XMLInputFactory.newInstance();
        List<DasFeature> features = new ArrayList<DasFeature>();
        try {
            features = DasParser.getAllDasFeatures(xmlParseFactory.createXMLEventReader(new URL("http://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/features?segment=" + anUniprotAccession).openStream()));
        } catch (XMLStreamException ex) {
            Logger.getLogger(UniprotSecondaryStructurePrediction.class.getName()).log(Level.SEVERE, null, ex);
        }
        Collections.sort(features, new CompareDasFeatures());
        int aminoAcidLocation = 0;
        for (DasFeature feature : features) {
            if ((secStructMap.containsKey(feature.getType()))) {
                do {
                    predictionResult.append("-");
                    aminoAcidLocation++;
                } while (aminoAcidLocation < feature.getStart());
                do {
                    predictionResult.append(secStructMap.get(feature.getType()));
                    aminoAcidLocation++;
                } while (aminoAcidLocation < feature.getEnd());
            }
        }
        return predictionResult.toString();
    }
}
