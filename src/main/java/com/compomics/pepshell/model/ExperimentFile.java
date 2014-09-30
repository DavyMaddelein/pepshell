package com.compomics.pepshell.model;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author svend
 */
public class ExperimentFile extends File {

    private Map<String,Integer> fileAnnotationData = new HashMap<>();
    
    public ExperimentFile(String pathname) {
        super(pathname);
    }

    public ExperimentFile(String parent, String child) {
        super(parent, child);
    }

    public ExperimentFile(File parent, String child) {
        super(parent, child);
    }

    public ExperimentFile(URI uri) {
        super(uri);
    }
    
    public Map<String,Integer> getFileAnnotations(){
        return fileAnnotationData;
    }
}
