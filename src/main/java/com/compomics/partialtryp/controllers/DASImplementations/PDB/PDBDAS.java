package com.compomics.partialtryp.controllers.DASImplementations.PDB;

import com.compomics.partialtryp.controllers.DAO.DasParser;
import com.compomics.partialtryp.model.DAS.PDBDAS.DasAlignment;
import com.compomics.partialtryp.model.DAS.PDBDAS.DasBlock;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Davy
 */
public class PDBDAS extends DasParser {
    
        public static List<DasAlignment> getAllAlignments(String dasXMLFile){
        int lastFeatureEndPosition = 0;
        List<DasAlignment> alignments = new ArrayList<DasAlignment>();
        while(dasXMLFile.indexOf("<alignment alignType=\"PDB_SP\">",lastFeatureEndPosition + 30) != -1){
            String alignment = dasXMLFile.substring(dasXMLFile.indexOf("<alignment alignType=\"PDB_SP\">",lastFeatureEndPosition),dasXMLFile.indexOf("</alignment>",lastFeatureEndPosition) +12 );
            lastFeatureEndPosition = dasXMLFile.indexOf("</alignment>",lastFeatureEndPosition) + 5;
            alignments.add(new DasAlignment(alignment));
        }
        return alignments;
    }
    
        public static List<DasBlock> getAllBlocks(String dasXMLFile){
        List<DasBlock> blocks = new ArrayList<DasBlock>();
        int startBlock = 0;
        while (dasXMLFile.indexOf("<block", startBlock) > -1) {
            String blockStr = dasXMLFile.substring(dasXMLFile.indexOf(">", dasXMLFile.indexOf("<block", startBlock)), dasXMLFile.indexOf("</block", startBlock));
            startBlock = dasXMLFile.indexOf("</block", startBlock) + 5;
            String segment1 = blockStr.substring(blockStr.indexOf("<segment"), blockStr.indexOf(">", blockStr.indexOf("<segment")) + 1);
            int segment1End = blockStr.indexOf(">", blockStr.indexOf("<segment")) + 1;
            String segment2 = blockStr.substring(blockStr.indexOf("<segment", segment1End), blockStr.indexOf(">", blockStr.indexOf("<segment", segment1End)) + 1);
            String pdbA = segment1.substring(segment1.indexOf("Id=\"") + 4, segment1.indexOf("\"", segment1.indexOf("Id=\"") + 4));
            String startStrPdb = segment1.substring(segment1.indexOf("rt=\"") + 4, segment1.indexOf("\"", segment1.indexOf("rt=\"") + 4));
            String endStrPdb = segment1.substring(segment1.indexOf("nd=\"") + 4, segment1.indexOf("\"", segment1.indexOf("nd=\"") + 4));
            int startPdb = Integer.valueOf(startStrPdb);
            int endPdb = Integer.valueOf(endStrPdb);
            String spA = segment2.substring(segment2.indexOf("Id=\"") + 4, segment2.indexOf("\"", segment2.indexOf("Id=\"") + 4));
            String startStrSp = segment2.substring(segment2.indexOf("rt=\"") + 4, segment2.indexOf("\"", segment2.indexOf("rt=\"") + 4));
            String endStrSp = segment2.substring(segment2.indexOf("nd=\"") + 4, segment2.indexOf("\"", segment2.indexOf("nd=\"") + 4));
            int startSp = Integer.valueOf(startStrSp);
            int endSp = Integer.valueOf(endStrSp);

            DasBlock align = new DasBlock(startPdb, endPdb, startSp, endSp, pdbA, spA);
            blocks.add(align);
        }

        return blocks;
    }    
}
