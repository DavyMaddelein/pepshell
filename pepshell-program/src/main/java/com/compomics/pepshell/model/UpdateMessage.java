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

package com.compomics.pepshell.model;

/**
 *
 * @author Davy Maddelein
 */
public class UpdateMessage {

    private boolean repaint = false;
    private String message = "";
    private boolean informUser = false;

    /**
     * creates an empty update message which does not flag to repaint and has an
     * empty message
     */
    public UpdateMessage() {
        //empty standard constructor
    }

    /**
     * creates an update message object to notify various levels in the program
     *
     * @param dataVisiblyChanged boolean to flag repainting gui
     * @param aMessage update message, ignores nulls
     */
    public UpdateMessage(boolean dataVisiblyChanged, String aMessage, boolean informUserOfChange) {
        this.repaint = dataVisiblyChanged;
        if (aMessage != null) {
            this.message = aMessage;
        }
        this.informUser = informUserOfChange;
    }

    public boolean repaint() {
        return repaint;
    }

    public String getMessage() {
        return message;
    }

    public boolean informUser() {
        return informUser;
    }
}
