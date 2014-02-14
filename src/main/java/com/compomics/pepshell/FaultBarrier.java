package com.compomics.pepshell;

import java.util.Observable;
import org.apache.log4j.Logger;

/**
 *
 * @author Davy
 */
public class FaultBarrier extends Observable {

    private static final FaultBarrier instance = new FaultBarrier();
    private static final Logger logger = Logger.getLogger(FaultBarrier.class);

    //TODO needs a lot more work since this is being used pretty much everywhere
    public final void handleException(Exception e) {
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
}