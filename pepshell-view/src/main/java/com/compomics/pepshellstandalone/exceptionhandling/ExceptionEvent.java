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

package com.compomics.pepshellstandalone.exceptionhandling;


import org.apache.logging.log4j.Level;

/**
 * Created by Davy Maddelein on 09/07/2015.
 */
public class ExceptionEvent {

    private final String event;
    private final boolean informUser;
    private final Level level;

    public ExceptionEvent(String event, boolean informUser){
        this(event,informUser,Level.ERROR);
    }

    public ExceptionEvent(String event,boolean informUser,Level loggingLevel) {
        this.informUser = informUser;
        this.event = event;
        this.level = loggingLevel;
    }

    public String getEvent() {
        return event;
    }

    public boolean isInformUser() {
        return informUser;
    }

    public Level getLoggingLevel(){
        return level;
    }
}
