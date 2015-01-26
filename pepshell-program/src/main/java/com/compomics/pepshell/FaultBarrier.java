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

package com.compomics.pepshell;

import java.io.IOException;
import java.util.Observable;

import com.compomics.pepshell.model.UpdateMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author Davy Maddelein
 */
public class FaultBarrier extends Observable {

    private static final FaultBarrier instance = new FaultBarrier();
    private static final Logger logger = Logger.getLogger(FaultBarrier.class);

    private FaultBarrier(){};


    //TODO needs a lot more work since this is being used pretty much everywhere
    public final void handleException(Exception e) {
        logger.error(e);
        e.printStackTrace();
        setChanged();
        notifyObservers(e.getMessage());
    }

    public final void handleException(Exception e,UpdateMessage message){
        logger.error(e);
        e.printStackTrace();
        if (message.informUser()){
            setChanged();
        notifyObservers(message.getMessage());}

    }

    public final void handleException(Exception e,boolean isRecoverable) {
        logger.error(e);
        e.printStackTrace();
        setChanged();
        notifyObservers(e.getMessage());
    }
    
    public final void handleExceptionAndSendMail(Exception e) {
        logger.error(e);
        //Mailer.sendMail(,);
    }

    public static FaultBarrier getInstance() {
        return instance;
    }

    public void handlePartialFailure(IOException ioException) {
        logger.warn(ioException);
    }
}