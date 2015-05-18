/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.compomics.pepshell.view.components;

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
            instance = new JFileChooserWithMemory();
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
        }
        return currentlySelectedFiles;
    }

    @Override
    public File getSelectedFile() {
        previousLocation = super.getSelectedFile();
        return previousLocation;
    }

}
