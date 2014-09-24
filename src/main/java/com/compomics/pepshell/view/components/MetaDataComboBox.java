/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.view.components;

import com.compomics.pepshell.model.enums.PossiblePeptideMetaDataAnnotationsEnum;
import javax.swing.JComboBox;

/**
 *
 * @author Davy Maddelein
 */
public class MetaDataComboBox extends JComboBox<PossiblePeptideMetaDataAnnotationsEnum> {

    public MetaDataComboBox() {
        super();
        for (PossiblePeptideMetaDataAnnotationsEnum anEnum : PossiblePeptideMetaDataAnnotationsEnum.values()) {
            this.addItem(anEnum);
        }
    }
}
