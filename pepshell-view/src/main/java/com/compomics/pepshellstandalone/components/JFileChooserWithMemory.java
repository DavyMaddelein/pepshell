/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshellstandalone.components;

import com.compomics.pepshell.model.Property;
import com.compomics.pepshellstandalone.ViewProperties;
import com.compomics.pepshellstandalone.ViewPropertyEnum;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * @author Davy Maddelein
 */
public class JFileChooserWithMemory extends JFileChooser {

    //TODO change color scheme to white

    private File previousLocation;

    private static JFileChooserWithMemory instance;

    public static JFileChooserWithMemory getInstance() {
        if (instance == null) {
            instance = new JFileChooserWithMemory(new File(ViewProperties.getInstance().getProperty("view.lastloadingdirectory")));
        }
        return instance;
    }

    public JFileChooserWithMemory() {
        super();
    }

    public JFileChooserWithMemory(String currentDirectoryPath) {
        super(currentDirectoryPath);
        previousLocation = new File(currentDirectoryPath);
    }

    public JFileChooserWithMemory(File currentDirectory) {
        super(currentDirectory);
        previousLocation = currentDirectory;
    }

    public JFileChooserWithMemory(FileSystemView fsv) {
        super(fsv);
        previousLocation = fsv.getHomeDirectory();
    }

    public JFileChooserWithMemory(File currentDirectory, FileSystemView fsv) {
        super(currentDirectory, fsv);
        previousLocation = currentDirectory;
    }

    public JFileChooserWithMemory(String currentDirectoryPath, FileSystemView fsv) {
        super(currentDirectoryPath, fsv);
        previousLocation = new File(currentDirectoryPath);
    }

    @Override
    public int showDialog(Component parent, String approveButtonText) throws HeadlessException {
        if (getCurrentDirectory() == null || !getCurrentDirectory().exists()) {
            setCurrentDirectory(previousLocation);
        }
        return super.showDialog(parent, approveButtonText);
    }

    @Override
    public int showSaveDialog(Component parent) throws HeadlessException {
        if (getCurrentDirectory() == null || !getCurrentDirectory().exists()) {
            setCurrentDirectory(previousLocation);
        }
        return super.showSaveDialog(parent);
    }

    @Override
    public int showOpenDialog(Component parent) throws HeadlessException {
        if (getCurrentDirectory() == null || !getCurrentDirectory().exists()) {
            setCurrentDirectory(previousLocation);
        }
        return super.showOpenDialog(parent);
    }

    @Override
    public File[] getSelectedFiles() {
        File[] currentlySelectedFiles = super.getSelectedFiles();
        if (currentlySelectedFiles.length > 0) {
            previousLocation = currentlySelectedFiles[currentlySelectedFiles.length - 1];
            if (previousLocation != null) {
                ViewProperties.getInstance().setProperty(new Property(ViewPropertyEnum.LASTUSEDLOADINGDIRECTORY, super.getSelectedFile().getAbsolutePath()));
            }
        }
        return currentlySelectedFiles;
    }

    @Override
    public File getSelectedFile() {
        previousLocation = super.getSelectedFile();
        if (previousLocation != null) {
            ViewProperties.getInstance().setProperty(new Property(ViewPropertyEnum.LASTUSEDLOADINGDIRECTORY, super.getSelectedFile().getAbsolutePath()));
        }
        return previousLocation;
    }

}
