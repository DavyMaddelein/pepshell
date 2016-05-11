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

package com.compomics.pepshellstandalone.componentmodels;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import java.util.regex.Pattern;

/**
 * Created by Davy Maddelein on 21/04/2015.
 */
public class ClassBasedFilter extends DocumentFilter {

    private Class filterClass;
    private Pattern regex;

    public ClassBasedFilter(Class clazz) {
        super();
        filterClass = clazz;
    }

    @Override
    public void insertString(DocumentFilter.FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);
        if (sb.length() == 0 || test(sb.toString())) {
            super.insertString(fb, offset, string, attr);
        } else {
            JOptionPane.showMessageDialog(null, "not a number");
        }
    }

    private boolean test(String text) {
        try {
            if (filterClass == String.class){
                //todo something something regex
            }
            else if (filterClass == Integer.class || filterClass == int.class){
                Integer.parseInt(text);
            } else if (filterClass == Double.class || filterClass == double.class){
                Double.parseDouble(text);
            } else if (filterClass == Float.class || filterClass == float.class){
                Float.parseFloat(text);
            } else if (filterClass == Boolean.class || filterClass == boolean.class){
                Boolean.parseBoolean(text);
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }



    @Override
    public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {

        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);

        if (sb.length() == 0 || test(sb.toString())) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            JOptionPane.showMessageDialog(null, "text needs to be "+filterClass.getName().toLowerCase());
        }

    }

    @Override
    public void remove(DocumentFilter.FilterBypass fb, int offset, int length)
            throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);

        if (sb.length() == 0 || test(sb.toString())) {
            super.remove(fb, offset, length);
        } else {
            JOptionPane.showMessageDialog(null, "text needs to be "+ filterClass.getName().toLowerCase());
        }

    }

    public void setAdditionalRegexFilter(Pattern aPattern){
        this.regex = aPattern;
    }

}
