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

import com.compomics.pepshell.controllers.DataSources.AbstractDataSource;
import com.compomics.pepshell.controllers.DataSources.DataBaseDataSource;
import com.compomics.pepshell.controllers.DataSources.FileDataSource;

/**
 *
 * @author Davy Maddelein
 */
    public enum DataSourceEnum {

        FILE(new FileDataSource()), DATABASE(new DataBaseDataSource());
        AbstractDataSource dataSource;

        DataSourceEnum(AbstractDataSource aDataSource) {
            this.dataSource = aDataSource;
        }

        public AbstractDataSource getDataSource() {
            return dataSource;
        }
    }
