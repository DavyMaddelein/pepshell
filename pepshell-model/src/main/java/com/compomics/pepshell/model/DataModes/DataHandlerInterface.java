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

package com.compomics.pepshell.model.DataModes;

import com.compomics.pepshell.model.Experiment;
import com.compomics.pepshell.model.exceptions.DataRetrievalException;

import java.io.IOException;

/**
 * Created by Davy Maddelein on 04/12/2014.
 */
public interface DataHandlerInterface<T> {

    boolean canHandle(T objectToHandle) throws IOException;

    Experiment addData(Experiment anExperiment) throws DataRetrievalException;
}
