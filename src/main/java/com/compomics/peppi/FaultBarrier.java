package com.compomics.peppi;


import java.util.Observable;
import org.apache.log4j.Logger;


/**
 *
 * @author Davy
 */
public class FaultBarrier extends Observable {
    private static final FaultBarrier instance = new FaultBarrier();
    private static final Logger logger = Logger.getLogger(FaultBarrier.class);

    public final void handleError(Exception e){
        logger.error(e);
        setChanged();
        notifyObservers(e.getMessage());
    }  
    
    public static FaultBarrier getInstance(){
        return instance;
    }
}