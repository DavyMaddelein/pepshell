/*
 * Copyright 2014 Davy Maddelein.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.compomics.pepshell.controllers.DASImplementations.PDB;

import com.compomics.pepshell.controllers.DAO.DasParser;
import com.compomics.pepshell.model.DAS.PDBDAS.DasAlignment;
import com.compomics.pepshell.model.DAS.PDBDAS.DasBlock;
import java.util.ArrayList;
import java.util.List;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Davy Maddelein
 */
class PDBDAS extends DasParser {

    //TODO redo this with an xml parser
    public static List<DasAlignment> getAllAlignments(XMLEventReader dasXMLFile) throws XMLStreamException {
        List<DasAlignment> alignments = new ArrayList<>();
        XMLEvent dasEvent;
        while (dasXMLFile.hasNext()) {
            if ((dasEvent = dasXMLFile.nextEvent()).isStartElement()) {
                if (dasEvent.asStartElement().getName().getLocalPart().equalsIgnoreCase("alignment")) {
                    if (dasEvent.asStartElement().getAttributeByName(new QName("alignType")).getValue().equalsIgnoreCase("PDB_SP")) {
                        dasEvent = dasXMLFile.nextEvent();
                        alignments.add(new DasAlignment(dasEvent.asCharacters().getData()));
                    }
                }
            }
        }
        return alignments;
    }

    public static List<DasBlock> getAllBlocks(XMLEventReader dasXMLFile) throws XMLStreamException {
        List<DasBlock> blocks = new ArrayList<>();
        XMLEvent dasEvent;
        
        while(dasXMLFile.hasNext()){
            if((dasEvent = dasXMLFile.nextEvent()).isStartElement()){
                if(dasEvent.asStartElement().getName().getLocalPart().equalsIgnoreCase("block")){
                    while(dasXMLFile.hasNext()){
                    
                    }
                }
            }
        }
        
        /**
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
        }*/

        return blocks;
    }
}
