/*
 * Copyright 2014 svend.
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
package com.compomics.pepshell.model.enums;

import com.compomics.pepshell.controllers.DataModes.AbstractDataMode;
import com.compomics.pepshell.controllers.DataModes.CoLimsDataMode;
import com.compomics.pepshell.controllers.DataModes.FastaDataMode;
import com.compomics.pepshell.controllers.DataModes.MsLimsDataMode;

/**
 *
 * @author Davy Maddelein
 */
public enum DataModeEnum {
         MSLIMS(new MsLimsDataMode()), COLIMS(new CoLimsDataMode()), FASTA(new FastaDataMode());
        private final AbstractDataMode dataMode;

        DataModeEnum(AbstractDataMode aDataMode) {
            this.dataMode = aDataMode;
        }

        public AbstractDataMode getDataMode() {
            return dataMode;
        }
}
