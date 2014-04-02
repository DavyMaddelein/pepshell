package com.compomics.pepshell.view.componentmodels;

import com.compomics.pepshell.controllers.InfoFinders.DataRetrievalStep;
import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

/**
 *
 * @author Davy
 */
public class DataRetrievalStepsDialogListCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component tempComponent = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof DataRetrievalStep){
            if (((DataRetrievalStep)value).executeStep()){
                
            } else {
                tempComponent.setForeground(Color.red);
            }
        }
        return tempComponent; 
    }


    
    
    
}
