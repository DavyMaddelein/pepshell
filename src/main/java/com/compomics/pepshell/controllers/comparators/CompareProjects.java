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

package com.compomics.pepshell.controllers.comparators;

import com.compomics.pepshell.model.Experiment;
import java.io.Serializable;
import java.util.Comparator;

/**
 *
 * @author Davy Maddelein
 */
class CompareProjects implements Comparator<Experiment>,Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Experiment firstProject, Experiment secondProject) {
        return firstProject.toString().compareTo(secondProject.toString());
    }

    
}
