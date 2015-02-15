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

package com.compomics.pepshell.model.exceptions;

import com.compomics.pepshell.model.enums.DataSourceEnum;

/**
 * Created by Davy Maddelein on 26/11/2014.
 */
public class ExperimentMetaData {

    private DataSourceEnum dataSource;

    protected ExperimentMetaData(DataSourceEnum aDataSource){
            dataSource = aDataSource;
    }

    public ExperimentMetaData mergeAnnotations(ExperimentMetaData annotation){

        return this;
    }
}
