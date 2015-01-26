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

package autoupdater;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.XMLEvent;

/**
 * MetaDataXMLParser.
 *
 * @author Davy Maddelein
 */
public class MetaDataXMLParser {

    /**
     * The highest version number.
     */
    private String highestVersionNumber;
    /**
     * The XML event.
     */
    private XMLEvent XMLEvent;

    /**
     * Create a new MetaDataXMLParser.
     *
     * @param xmlReader the XML reader
     * @throws XMLStreamException
     */
    public MetaDataXMLParser(XMLEventReader xmlReader) throws XMLStreamException {
        while (xmlReader.hasNext()) {
            XMLEvent = xmlReader.nextEvent();
            if (XMLEvent.isStartElement()) {
                if (XMLEvent.asStartElement().getName().getLocalPart().equalsIgnoreCase("versions")) {
                    parseVersionNumbers(xmlReader);
                    break;
                }
            }
        }
    }

    /**
     * Returns the highest version number.
     *
     * @return the highest version number
     */
    public String getHighestVersionNumber() {
        return highestVersionNumber;
    }

    /**
     * Parses the version numbers of a maven repository website (or just about
     * any proper xml containing the tag version).
     *
     * @param xmlReader
     * @throws XMLStreamException
     */
    private void parseVersionNumbers(XMLEventReader xmlReader) throws XMLStreamException {
        CompareVersionNumbers versionNumberComparator = new CompareVersionNumbers();
        while (xmlReader.hasNext()) {
            XMLEvent = xmlReader.nextEvent();
            if (XMLEvent.isStartElement()) {
                if (XMLEvent.asStartElement().getName().getLocalPart().equalsIgnoreCase("version")) {

                    String currentVersionNumber = xmlReader.nextEvent().asCharacters().getData();

                    if (!currentVersionNumber.contains("b") && !currentVersionNumber.contains("beta")) {
                        if (highestVersionNumber == null) {
                            highestVersionNumber = currentVersionNumber;
                        } else {
                            if (versionNumberComparator.compare(highestVersionNumber, currentVersionNumber) == 1) {
                                highestVersionNumber = currentVersionNumber;
                            }
                        }
                    }
                }
            } else if (XMLEvent.isEndElement()) {
                if (XMLEvent.asEndElement().getName().getLocalPart().equalsIgnoreCase("versions")) {
                    break;
                }
            }
        }
    }
}
