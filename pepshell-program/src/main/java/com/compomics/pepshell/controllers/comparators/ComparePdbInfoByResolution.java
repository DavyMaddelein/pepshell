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

import com.compomics.pepshell.model.PdbInfo;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 *
 * @author Davy Maddelein
 */
public class ComparePdbInfoByResolution implements Comparator<PdbInfo>,Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(PdbInfo o1, PdbInfo o2) {
        if (Objects.equals(o1.getResolution(), o2.getResolution())) {
            return 0;
        } else if (o1.getResolution() > o2.getResolution()) {
            return 1;
        } else {
            return -1;
        }
    }

}
