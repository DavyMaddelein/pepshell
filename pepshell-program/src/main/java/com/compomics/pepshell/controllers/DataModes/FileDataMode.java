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

package com.compomics.pepshell.controllers.DataModes;

import com.compomics.pepshell.controllers.DataSources.StructureDataSources.ExternalStructureDataSource;
import com.compomics.pepshell.controllers.ViewPreparation.DataRetrievalForFasta;
import java.io.File;

/**
 *
 * @author Davy Maddelein
 */
public class FileDataMode extends AbstractDataMode {

    private File fastaFile;

    public FileDataMode() {
        super(new DataRetrievalForFasta(),new ExternalStructureDataSource(),null);
    }

    public FileDataMode(File aFastaFile) {
        super(new DataRetrievalForFasta(), new ExternalStructureDataSource(),null);
        this.fastaFile = aFastaFile;
    }

    public File getFastaFile() {
        return fastaFile;
    }

    public void setFastaFile(File aFastaFile) {
        fastaFile = aFastaFile;
        ((DataRetrievalForFasta) this.getViewPreparationForMode()).setFastaFile(aFastaFile);
    }
}
