package com.compomics.pepshell.controllers.secondarystructureprediction;

import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.DasParser;
import com.compomics.pepshell.controllers.DAO.URLController;
import static com.compomics.pepshell.controllers.secondarystructureprediction.SecondaryStructurePrediction.secStructMap;
import com.compomics.pepshell.controllers.comparators.CompareDasFeatures;
import com.compomics.pepshell.model.DAS.DasFeature;
import com.compomics.pepshell.model.exceptions.ConversionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.stream.XMLStreamException;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/6/13 Time: 1:11 PM To change
 * this template use File | Settings | File Templates.
 */
public class UniprotSecondaryStructurePrediction extends SecondaryStructurePrediction {

    @Override
    public String getPrediction(String anUniprotAccession) throws IOException {

        StringBuilder predictionResult = new StringBuilder();
        List<DasFeature> features = new ArrayList<DasFeature>();
        try {
            features = DasParser.getAllDasFeatures(URLController.readUrl("http://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/features?segment=" + AccessionConverter.toUniprot(anUniprotAccession)));
        } catch (XMLStreamException ex) {
            Logger.getLogger(UniprotSecondaryStructurePrediction.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ConversionException ex) {
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
