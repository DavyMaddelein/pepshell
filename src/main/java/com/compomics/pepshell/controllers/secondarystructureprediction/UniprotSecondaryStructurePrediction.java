package com.compomics.pepshell.controllers.secondarystructureprediction;

import com.compomics.pepshell.FaultBarrier;
import com.compomics.pepshell.controllers.AccessionConverter;
import com.compomics.pepshell.controllers.DAO.DasParser;
import com.compomics.pepshell.controllers.DAO.DAUtils.WebUtils;
import com.compomics.pepshell.controllers.comparators.CompareDasFeatures;
import com.compomics.pepshell.model.DAS.DasFeature;
import com.compomics.pepshell.model.exceptions.ConversionException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/6/13 Time: 1:11 PM To change
 * this template use File | Settings | File Templates.
 */
public class UniprotSecondaryStructurePrediction extends SecondaryStructurePrediction {

    @Override
    public List<String> getPrediction(String anUniprotAccession) throws IOException {

        List<String> predictionResult = new ArrayList<>();
        List<DasFeature> features = new ArrayList<>();
        try {
            features = DasParser.getAllDasFeatures(WebUtils.getHTMLPage("http://www.ebi.ac.uk/das-srv/uniprot/das/uniprot/features?segment=" + AccessionConverter.toUniprot(anUniprotAccession)));
        } catch (ConversionException ex) {
            FaultBarrier.getInstance().handleException(ex);
        }
        Collections.sort(features, new CompareDasFeatures());
        int aminoAcidLocation = 0;
        for (DasFeature feature : features) {
            if ((secStructMap.containsKey(feature.getType()))) {
                do {
                    predictionResult.add(secStructMap.get("coil"));
                    aminoAcidLocation++;
                } while (aminoAcidLocation < feature.getStart());
                do {
                    predictionResult.add(secStructMap.get(feature.getType()));
                    aminoAcidLocation++;
                } while (aminoAcidLocation < feature.getEnd());
            }
        }
        return predictionResult;
    }
}
