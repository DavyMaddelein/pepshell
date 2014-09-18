package com.compomics.pepshell.model;

/**
 *
 * @author Davy
 */
public class UpdateMessage {

    private boolean repaint = false;
    private String message = "";

    /**
     * creates an empty update message which does not flag to repaint and has an
     * empty message
     */
    public UpdateMessage() {
        //empty standard constructor
    }

    /**
     * creates an update message object to notify various levels in the program
     * @param dataVisiblyChanged boolean to flag repainting gui
     * @param aMessage update message, ignores nulls
     */
    public UpdateMessage(boolean dataVisiblyChanged, String aMessage) {
        this.repaint = dataVisiblyChanged;
        if (aMessage != null) {
            this.message = aMessage;
        }
    }

    public boolean repaint() {
        return repaint;
    }

    public String getMessage() {
        return message;
    }
}
