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

import java.io.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import net.jimmc.jshortcut.JShellLink;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;

/**
 * FileDAO file access.
 *
 * @author Davy Maddelein
 */
public abstract class FileDAO {

    /**
     * Creates a new Desktop Shortcut to the maven jar file, atm windows only.
     *
     * @param file the maven jarfile to make a shortcut to
     * @param iconName the name of the icon file in the resources folder
     * @param toolName the name of the tool, e.g., PeptideShaker
     * @param deleteOldShortcut if previous shortcuts containing the maven jar
     * file artifact id should be removed
     * @return true id the shortcut was created (?)
     * @throws IOException
     */
    public abstract boolean createDesktopShortcut(MavenJarFile file, String iconName, String toolName, boolean deleteOldShortcut) throws IOException;

    /**
     * Add desktop shortcut.
     *
     * @param mavenJarFile the maven jar file
     * @return true id the shortcut was created (?)
     */
    public boolean addShortcutAtDeskTop(MavenJarFile mavenJarFile) {
        return addShortcutAtDeskTop(mavenJarFile, null);
    }

    /**
     * Adds a shortcut to the desktop. At the moment for Windows only.
     *
     * @param mavenJarFile the {@code MavenJarFile} to create the shortcut for
     * @param iconName the name of the icon in the resource folder of the
     * {@code MavenJarFile} to link to
     * @return true if the shortcut was created otherwise false
     * @throws NullPointerException
     * @throws RuntimeException
     */
    public boolean addShortcutAtDeskTop(MavenJarFile mavenJarFile, String iconName) throws NullPointerException, RuntimeException {
        String os = System.getProperty("os.name").toLowerCase(new Locale("en"));
        if (os.contains("win")) {

            JShellLink link = new JShellLink();
        link.setFolder(JShellLink.getDirectory("desktop"));
        link.setName(new StringBuilder().append(mavenJarFile.getArtifactId()).append("-").append(mavenJarFile.getVersionNumber()).toString());
        if (iconName != null) {
            link.setIconLocation(new StringBuilder().append(new File(mavenJarFile.getAbsoluteFilePath()).getParentFile().getAbsolutePath()).append("/resources/").append(iconName).toString());
        }
        link.setPath(mavenJarFile.getAbsoluteFilePath());
        link.save();
        } else if (os.contains("nix") || os.contains("nux") || os.contains("aix")){
            /**
             * To manually create a desktop shortcut for a particular program or command, you can create a .desktop file using any text editor, and place it in either /usr/share/applications or ~/.local/share/applications. A typical .desktop file looks like the following.

             [Desktop Entry]
             Encoding=UTF-8
             Version=1.0                                     # version of an app.
             Name[en_US]=yEd                                 # name of an app.
             GenericName=GUI Port Scanner                    # longer name of an app.
             Exec=java -jar /opt/yed-3.11.1/yed.jar          # command used to launch an app.
             Terminal=false                                  # whether an app requires to be run in a terminal.
             Icon[en_US]=/opt/yed-3.11.1/icons/yicon32.png   # location of icon file.
             Type=Application                                # type.
             Categories=Application;Network;Security;        # categories in which this app should be listed.
             Comment[en_US]=yEd Graph Editor                 # comment which appears as a tooltip.

             */
        }

        return true;
    }

    /**
     * Try to find an at least somewhat sane location to download files to.
     *
     * @param targetDownloadFolder first place to check if it is a possible
     * download location
     * @return the folder to download in (in best case scenario this is the
     * passed parameter targetDownloadFolder)
     * @throws IOException
     */
    public abstract File getLocationToDownloadOnDisk(String targetDownloadFolder) throws IOException;


    public File inflateArchive(File source, File destination) throws IOException, ArchiveException {
        ArchiveInputStream stream = new ArchiveStreamFactory().createArchiveInputStream(new BufferedInputStream(new FileInputStream(source)));
        ZipFile zipfile = new ZipFile(source);
        ArchiveEntry entry = null;
        FileOutputStream dest = null;
        InputStream inStream = null;
        if ((entry = stream.getNextEntry()) != null) {
            do {
                File destFile = new File(destination, entry.getName());
                if (!destFile.getParentFile().exists()) {
                    if (!destFile.getParentFile().mkdirs()) {
                        throw new IOException("could not create the folders to unzip in");
                    }
                }
                if (!entry.isDirectory()) {
                    try {
                        dest = new FileOutputStream(destFile);
                        inStream = stream;
                        IOUtils.copyLarge(inStream, dest);
                    } finally {
                        if (dest != null) {
                            dest.close();
                        }
                        if (inStream != null) {
                            inStream.close();
                        }
                    }
                } else {
                    if (!destFile.exists()) {
                        if (!destFile.mkdirs()) {
                            throw new IOException("could not create folders to unzip file");
                        }
                    }
                }
            } while ((entry = stream.getNextEntry()) != null);
        }
        return destination;
    }


    // @TODO: rewrite both downloadAndUnzipFiles to use apache commons compress library?
    /**
     * Unzips a zip archive.
     *
     * @param zip the zipfile to unzip
     * @param fileLocationOnDiskToDownloadTo the folder to unzip in
     * @return true if successful
     * @throws IOException
     */
    public boolean unzipFile(ZipFile zip, File fileLocationOnDiskToDownloadTo) throws IOException {
        FileOutputStream dest = null;
        InputStream inStream = null;
        Enumeration<? extends ZipEntry> zipFileEnum = zip.entries();
        while (zipFileEnum.hasMoreElements()) {
            ZipEntry entry = zipFileEnum.nextElement();
            File destFile = new File(fileLocationOnDiskToDownloadTo, entry.getName());
            if (!destFile.getParentFile().exists()) {
                if (!destFile.getParentFile().mkdirs()) {
                    throw new IOException("could not create the folders to unzip in");
                }
            }
            if (!entry.isDirectory()) {
                try {
                    dest = new FileOutputStream(destFile);
                    inStream = zip.getInputStream(entry);
                    IOUtils.copyLarge(inStream, dest);
                } finally {
                    if (dest != null) {
                        dest.close();
                    }
                    if (inStream != null) {
                        inStream.close();
                    }
                }
            } else {
                if (!destFile.exists()) {
                    if (!destFile.mkdirs()) {
                        throw new IOException("could not create folders to unzip file");
                    }
                }
            }
        }
        zip.close();
        return true;
    }

    /**
     * Untars and ungzips a .tar.gz file.
     *
     * @param in a {@code GZIPInputStream} of the file that needs to be
     * ungzipped and untarred
     * @param fileLocationOnDiskToDownloadTo the file to ungzip and untar to
     * @return true if successful
     * @throws IOException
     */
    public boolean unGzipAndUntarFile(GZIPInputStream in, File fileLocationOnDiskToDownloadTo) throws IOException {

        try {
            FileOutputStream fos = new FileOutputStream(fileLocationOnDiskToDownloadTo);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            //close resources
            fos.close();
            in.close();

            untar(fileLocationOnDiskToDownloadTo);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * Untars a .tar.
     *
     * @param fileToUntar
     * @return true if successful
     * @throws FileNotFoundException
     * @throws IOException
     */
    private boolean untar(File fileToUntar) throws FileNotFoundException, IOException {
        boolean fileUntarred = false;
        String untarLocation = fileToUntar.getParentFile().getAbsolutePath();
        TarArchiveInputStream tarStream = null;
        try {
            tarStream = new TarArchiveInputStream(new FileInputStream(fileToUntar));
            BufferedReader bufferedTarReader = null;
            try {
                bufferedTarReader = new BufferedReader(new InputStreamReader(tarStream));
                ArchiveEntry entry;
                while ((entry = tarStream.getNextEntry()) != null) {
                    byte[] buffer = new byte[8 * 1024];
                    File tempFile = new File(String.format("%s/%s", untarLocation, entry.getName()));
                    if (entry.isDirectory()) {
                        if (!tempFile.exists()) {
                            tempFile.mkdir();
                        }
                    } else {
                        OutputStream output = new FileOutputStream(tempFile);
                        try {
                            int bytesRead;
                            while ((bytesRead = tarStream.read(buffer)) != -1) {
                                output.write(buffer, 0, bytesRead);
                            }
                        } finally {
                            output.close();
                        }
                        tempFile.setExecutable(true); // make sure the binary files can be executed
                    }
                }
            } finally {
                if (bufferedTarReader != null) {
                    bufferedTarReader.close();
                }
            }
        } finally {
            if (tarStream != null) {
                tarStream.close();
            }
        }
        return fileUntarred;
    }

    /**
     * Fetches a maven built jar file from a folder for the given artifact id
     * (e.g peptideshaker or ms-lims).
     *
     * @param folder the folder to look in
     * @param artifactId the artifactid in the properties of the (@code
     * MavenJarFile) in the folder
     * @return the last found {@code MavenJarFile} with the given artifactid,
     * can be null
     * @throws IOException
     */
    public MavenJarFile getMavenJarFileFromFolderWithArtifactId(File folder, String artifactId) throws IOException {
        MavenJarFile mainJarFile = null;
        for (File aFile : folder.listFiles()) {
            if (aFile.isDirectory()) {
                mainJarFile = getMavenJarFileFromFolderWithArtifactId(aFile, artifactId);
                if (mainJarFile != null) {
                    break;
                }
            } else {
                if (aFile.getName().contains(artifactId) && aFile.getName().contains("jar")) {
                    mainJarFile = new MavenJarFile(aFile);
                    break;
                }
            }
        }
        /**
         * if (mainJarFile == null) { throw new IOException("could not find jar
         * file in folder and child folders"); }
         */
        return mainJarFile;
    }

    /**
     * Writes a stream to disk.
     *
     * @param in the stream to write to disk
     * @param name the name the file that will be created
     * @param outputLocationFolder the location to write to
     * @return the written file
     * @throws FileNotFoundException
     * @throws IOException
     */
    public File writeStreamToDisk(InputStream in, String name, File outputLocationFolder) throws FileNotFoundException, IOException {
        if (!outputLocationFolder.exists()) {
            if (!outputLocationFolder.mkdirs()) {
                throw new IOException("could not create the folders to write stream to disk");
            }
        }
        File outputFile = new File(outputLocationFolder, name);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(outputFile);
            IOUtils.copyLarge(in, out);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        in.close();
        return outputFile;
    }
}