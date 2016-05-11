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

package autoupdater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * GUIFileDAO.
 *
 * @author Davy Maddelein
 * @author Harald Barsnes
 */
public class GUIFileDAO extends FileDAO {

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean createDesktopShortcut(MavenJarFile file, String iconName, String toolName, boolean deleteOldShortcut) throws IOException {

        Properties compomicsArtifactProperties = new Properties();
        File compomicsArtifactPropertiesFile = new File(new StringBuilder().append(System.getProperty("user.home")).append("/.compomics/").append(file.getArtifactId()).append("/updatesettings.properties").toString());
        FileReader propFileReader = null;

        if (compomicsArtifactPropertiesFile.exists()) {
            try {
                propFileReader = new FileReader(compomicsArtifactPropertiesFile);
                if (compomicsArtifactPropertiesFile.exists()) {
                    compomicsArtifactProperties.load(propFileReader);
                }
            } finally {
                if (propFileReader != null) {
                    propFileReader.close();
                }
            }
        } else {
            compomicsArtifactProperties = new Properties();
        }

        if (!compomicsArtifactProperties.contains("create_shortcut")) {

            int selection = JOptionPane.showConfirmDialog(null,
                    "Create a shortcut to " + toolName + " on the desktop?",
                    "Create Desktop Shortcut?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE);

            //also check (as in add checkbox) to remember choice
            if (selection == JOptionPane.CANCEL_OPTION || selection == JOptionPane.CLOSED_OPTION) {
                compomicsArtifactProperties.setProperty("create_shortcut", String.valueOf(selection));
                FileOutputStream propOutputStream = null;
                try {
                    propOutputStream = new FileOutputStream(compomicsArtifactPropertiesFile);
                    compomicsArtifactProperties.store(propOutputStream, null);
                } finally {
                    if (propOutputStream != null) {
                        propOutputStream.close();
                    }
                }
            }
            try {
                // delete the add desktop icon trigger file for the new download
                String filePath = new StringBuilder().append(new File(file.getAbsoluteFilePath()).getParentFile().getAbsolutePath()).append("/resources/conf/firstRun").toString();
                if (new File(filePath).exists()) {
                    new File(filePath).delete();
                }
                if (selection == JOptionPane.YES_OPTION) {
                    //addShortcutAtDeskTop(file, iconName);

                }
                if (deleteOldShortcut) {
                    for (String fileName : new File(System.getProperty("user.home")).list()) {
                        // @TODO: do something here??
                    }
                }
            } catch (NullPointerException | NumberFormatException npe) {
                throw new IOException("could not create the shortcut");
            }
        } else {
            // @TODO: do something here??
        }

        return true;
    }

    /**
     *
     * {@inheritDoc }
     */
    @Override
    public File getLocationToDownloadOnDisk(String targetDownloadFolder) throws IOException {
        File file = new File(targetDownloadFolder).getParentFile();
        if (file.exists() && !file.isDirectory()) {
            file = file.getParentFile();
        }
        if (file == null) {
            Object[] options = {"Yes", "Specify Other Location", "Quit"};
            int choice = JOptionPane.showOptionDialog(null,
                    "Cannot find the location of the original file. Download\n"
                    + "to your home folder or specify another location?", "Download Location",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, JOptionPane.CANCEL_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                file = new File(System.getProperty("users.home"));
            } else if (choice == JOptionPane.NO_OPTION) {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("users.home"));
                fileChooser.setMultiSelectionEnabled(false);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fileChooser.setVisible(true);
                file = fileChooser.getSelectedFile();
            } else if (choice == JOptionPane.CANCEL_OPTION || choice == JOptionPane.CLOSED_OPTION) {
                throw new IOException("no download location");
            }
        }
        return file;
    }
}
