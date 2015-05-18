package com.compomics.pepshell;

import com.compomics.pepshell.model.AnnotatedFile;
import com.compomics.pepshell.model.FileBasedExperiment;
import com.compomics.pepshell.model.SeparatedValueExperimentMetadata;
/**
 * Created by Iain on 19/02/2015.
 */
public class ExperimentTestBase {

    public FileBasedExperiment setupExperiment(String experimentFile) {
        String testFile = this.getClass().getClassLoader().getResource("braf/" + experimentFile).getFile();

        SeparatedValueExperimentMetadata metadata = new SeparatedValueExperimentMetadata();
        metadata.setHasHeaders(true)
            .setPeptidesequenceColumn(1)
            .setProteinAccessionColumn(2)
            .setPeptideStartColumn(3)
            .setPeptideEndColumn(4)
            .setIntensityColumn(5)
            .setRatioColumn(6)
            .setValueSeparator("\t");

        AnnotatedFile aFile = new AnnotatedFile(testFile);
        aFile.addAnnotationsToFile(metadata);

        FileBasedExperiment testExperiment = new FileBasedExperiment("braf");
        testExperiment.setFile(aFile);

        return testExperiment;
    }

}
