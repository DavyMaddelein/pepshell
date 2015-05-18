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

import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;

import org.apache.commons.compress.archivers.zip.ZipFile;

import javax.swing.JOptionPane;
import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.FileUtils;

/**
 * Download the latest zip file from the repository.
 *
 * @author Davy Maddelein
 */
public class DownloadLatestZipFromRepo implements Runnable {

    /**
     * The downloaded version of the tool.
     */
    private static File downloadedFile;
    private static boolean startFileAfterDownload = true;
    private static boolean createDesktopShortcut = true;
    private static boolean deletePreviousVersion = true;
    private static URL downloadURL;
    private static FileDAO fileDAO;

    /**
     * Downloads the latest deploy from the genesis maven repository of the
     * artifact of the jarPath, starts it without arguments and removes the old
     * jar if there was an update.
     *
     * @param jarPath the path to the jarfile
     * @throws IOException        should there be problems with reading or writing
     *                            files during the updating
     * @throws XMLStreamException if there was a problem reading the meta data
     *                            from the remote maven repository
     * @throws URISyntaxException
     */
    public static void downloadLatestZipFromRepo(Path jarPath) throws IOException, XMLStreamException, URISyntaxException {
        downloadURL = new URL("http", "genesis.ugent.be", "/maven2/");
        downloadLatestZipFromRepo(jarPath, new String[0], downloadURL);
    }

    /**
     * Retrieves the latest version of a maven jar file from a maven repository,
     * also checks if the environment is headless or not.
     *
     * @param jarPath           the URL of the location of the jar that needs to be
     *                          updated on the file system. cannot be {@code null}
     * @param args              the args that will be passed to the newly downloaded program
     *                          when started, cannot be {@code null}
     * @param jarRepositoryRoot the maven repository to go look in, cannot be
     *                          {@code null}
     * @throws IOException        should there be problems with reading or writing
     *                            files during the updating
     * @throws XMLStreamException if there was a problem reading the meta data
     *                            from the remote maven repository
     * @throws URISyntaxException
     */
    public static void downloadLatestZipFromRepo(final Path jarPath, String[] args, URL jarRepositoryRoot) throws IOException, XMLStreamException, URISyntaxException {
        downloadURL = jarRepositoryRoot;
        if (GraphicsEnvironment.isHeadless()) {
            fileDAO = new HeadlessFileDAO();
        } else {
            fileDAO = new GUIFileDAO();
        }

    }

    public void setStartFileAfterDownload(boolean startFileAfterDownload) {
        DownloadLatestZipFromRepo.startFileAfterDownload = startFileAfterDownload;
    }

    public void setCreateDesktopShortcut(boolean createDesktopShortcut) {
        DownloadLatestZipFromRepo.createDesktopShortcut = createDesktopShortcut;
    }

    public void setDeletePreviousVersion(boolean deletePreviousVersion) {
        DownloadLatestZipFromRepo.deletePreviousVersion = deletePreviousVersion;
    }

    public void setDownloadURL(URL downloadURL) {
        DownloadLatestZipFromRepo.downloadURL = downloadURL;
    }

    public void setFileDAO(FileDAO fileDAO) {
        DownloadLatestZipFromRepo.fileDAO = fileDAO;
    }

    /**
     * Retrieves the latest version of a maven jar file from a maven repository.
     *
     * @param downloadFolder         the folder to download to
     * @param groupId                the group id
     * @param artifactId             the artifact id
     * @param iconName               name of the shortcut image should one be created
     * @param args                   the args that will be passed to the newly downloaded program
     *                               when started, cannot be {@code null}
     * @param jarRepository          the maven repository to go look in, cannot be
     *                               {@code null}
     * @param startDownloadedVersion if the newly downloaded version should be
     *                               started automatically or not
     * @param addDesktopIcon         if true, a desktop icon will be created
     * @param fileDAO                what implementation of FileDAO should be used in the
     *                               updating
     * @throws IOException        should there be problems with reading or writing
     *                            files during the updating
     * @throws XMLStreamException if there was a problem reading the meta data
     *                            from the remote maven repository
     * @throws URISyntaxException
     */
    public static void downloadLatestZipFromRepo(final File downloadFolder, String groupId, String artifactId, String iconName, String[] args, URL jarRepository, boolean startDownloadedVersion,
                                                 boolean addDesktopIcon, FileDAO fileDAO) throws IOException, XMLStreamException, URISyntaxException {

        //TL;DR of the next three lines: make the url for the latest version location of a maven jar file
        String artifactInRepoLocation = new StringBuilder(jarRepository.toExternalForm()).append(groupId.replaceAll("\\.", "/")).append("/").append(artifactId).toString();

        String latestRemoteRelease = WebDAO.getLatestVersionNumberFromRemoteRepo(new URL(new StringBuilder(artifactInRepoLocation).append("/maven-metadata.xml").toString()));

        String latestArtifactLocation = new StringBuilder(artifactInRepoLocation).append("/").append(latestRemoteRelease).toString();

        // download and unzip the files
        MavenJarFile downloadedJarFile = downloadAndUnzipJar(downloadFolder, artifactId, new URL(latestArtifactLocation), fileDAO,
                true, false);

        final File jarParent = downloadFolder;

        // add desktop icon
        if (addDesktopIcon) {
            fileDAO.createDesktopShortcut(downloadedJarFile, artifactId, iconName, false);
        }


        if (startDownloadedVersion) {
            launchJar(downloadedJarFile, args);
        }
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

    }

    /**
     * Simple jar launch through a {@code ProcessBuilder}.
     *
     * @param downloadedFile the downloaded jar file to start
     * @param args           the args to give to the jar file
     * @return true if the launch succeeded
     * @throws IOException if the process could not start
     */
    private static Process launchJar(MavenJarFile downloadedFile, String[] args) throws NullPointerException, IOException {
        Process jar;
        ProcessBuilder p;
        List<String> processToRun = new ArrayList<String>();
        try {
            processToRun.add("java");
            processToRun.add("-jar");
            processToRun.add(downloadedFile.getAbsoluteFilePath());
            if (args != null) {
                processToRun.addAll(Arrays.asList(args));
            }
            p = new ProcessBuilder(processToRun);
            p.directory(new File(downloadedFile.getAbsoluteFilePath()).getParentFile());
            jar = p.start();
        } catch (NullPointerException npe) {
            throw new IOException("could not start the jar");
        }
        return jar;
    }

    /**
     * Aggregation method for downloading and unzipping.
     *
     * @param mavenJarFile the maven jar file to download update for
     * @return the downloaded {@code MavenJarFile}
     * @throws MalformedURLException
     * @throws IOException
     * @throws XMLStreamException
     */
    private static MavenJarFile downloadAndUnzipJar(MavenJarFile mavenJarFile) throws MalformedURLException, IOException, XMLStreamException {
        boolean isWindows = false;

        if (System.getProperty("os.name").toLowerCase(new Locale("en")).contains("win")) {
            isWindows = true;
        }
        URL archiveURL;
        String folderName;

        // get the archive url
        if (isWindows) {
            archiveURL = WebDAO.getUrlOfZippedVersion(downloadURL, ".zip", false);
            folderName = archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/"), archiveURL.getFile().lastIndexOf(".zip"));
        } else {
            archiveURL = WebDAO.getUrlOfZippedVersion(downloadURL, ".tar.gz", false);
            if (archiveURL != null) {
                folderName = archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/"), archiveURL.getFile().lastIndexOf(".tar.gz"));
            } else {
                archiveURL = WebDAO.getUrlOfZippedVersion(downloadURL, ".zip", false);
                folderName = archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/"), archiveURL.getFile().lastIndexOf(".zip"));
                isWindows = true; // zip file, handling is same as for windows
            }
        }

        // special fix for tools with separate versions for windows and unix
        if (folderName.endsWith("-windows")) {
            folderName = folderName.substring(0, folderName.indexOf("-windows"));
        } else if (folderName.endsWith("-mac_and_linux")) {
            folderName = folderName.substring(0, folderName.indexOf("-mac_and_linux"));
        }

        // set up the folder to save the new download in
        File downloadFolder;
        if (isWindows) {
            downloadFolder = new File(fileDAO.getLocationToDownloadOnDisk(new File(mavenJarFile.getAbsoluteFilePath()).getParent()), folderName);
        } else {
            downloadFolder = fileDAO.getLocationToDownloadOnDisk(new File(mavenJarFile.getAbsoluteFilePath()).getParent());
        }
        if (!downloadFolder.exists()) {
            if (!downloadFolder.mkdirs()) {
                throw new IOException("could not make the directories needed to download the file in");
            }
        }

        // create an empty dummy file so that progress can be monitored
        downloadedFile = new File(downloadFolder, archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/")));

        // download and unzip the file
        if (isWindows) {
            downloadedFile = fileDAO.writeStreamToDisk(archiveURL.openStream(), archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/")), downloadFolder);
            fileDAO.unzipFile(new ZipFile(downloadedFile), downloadFolder.getParentFile());
        } else {
            fileDAO.unGzipAndUntarFile(new GZIPInputStream(archiveURL.openStream()), downloadedFile);
        }

        // get the new jar file
        MavenJarFile newMavenJar;
        if (isWindows) {
            newMavenJar = fileDAO.getMavenJarFileFromFolderWithArtifactId(downloadFolder, mavenJarFile.getArtifactId());
        } else {
            newMavenJar = fileDAO.getMavenJarFileFromFolderWithArtifactId(new File(downloadFolder, folderName), mavenJarFile.getArtifactId());
        }

        // delete the downloaded zip file
        if (deletePreviousVersion) {
            if (!downloadedFile.delete()) {
                throw new IOException("could not delete the zip file");
            }
        }

        return newMavenJar;
    }

    /**
     * @param artifactURL the url of the artifact, if the url is the actual file archive(zip,tar.gz or jar) to download it will just download,
     * otherwise it will try to first retrieve artifactid-versionnumber.zip/tar.gz
     * and lastly try to retrieve artifactid-versionnumber.jar
     * @return the downloaded file
     * @throws java.io.FileNotFoundException if there is not file to download at the url location
     */
    public static File downloadJarFile(URL artifactURL,File destination) throws IOException {

        URL archiveURL;

        // get the archive url
        String folderName;
        if (artifactURL.getPath().contains(".zip")) {
            archiveURL = WebDAO.getUrlOfZippedVersion(artifactURL, ".zip", false);
            folderName = archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/"), archiveURL.getFile().lastIndexOf(".zip"));
        } else {
            archiveURL = WebDAO.getUrlOfZippedVersion(artifactURL, ".tar.gz", false);
            if (archiveURL != null) {
                folderName = archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/"), archiveURL.getFile().lastIndexOf(".tar.gz"));
            }
        }

        // special fix for tools with separate versions for windows and unix, you don't do special fixes in a generic library
/*
        if (folderName.endsWith("-windows")) {
            folderName = folderName.substring(0, folderName.indexOf("-windows"));
        } else if (folderName.endsWith("-mac_and_linux")) {
            folderName = folderName.substring(0, folderName.indexOf("-mac_and_linux"));
        }
*/

        if (!destination.exists()) {
            if (!destination.mkdirs()) {
                throw new IOException("could not make the directories needed to download the file in");
            }
        }

        // create an empty dummy file so that progress can be monitored
        downloadedFile = new File(destination, archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/")));

        // download and unzip the file
        downloadedFile = fileDAO.writeStreamToDisk(archiveURL.openStream(), archiveURL.getFile().substring(archiveURL.getFile().lastIndexOf("/")), destination);
        return downloadedFile;
    }

    /**
     * Aggregation method for downloading and unzipping.
     *
     * @param jarURL the url of the version specific location
     * @param fileDAO       which fileDAO implementation that should be used
     * @param isWindows     if true, the OS will assumed to be windows
     *                      download
     * @return the downloaded {@code MavenJarFile}
     * @throws MalformedURLException
     * @throws IOException
     * @throws XMLStreamException
     */
    private static MavenJarFile downloadAndUnzipJar(final File downloadFolder, final String artifactId, URL jarURL,
                                                    FileDAO fileDAO, boolean cleanupZipFile, boolean isWindows) throws MalformedURLException, IOException, XMLStreamException {

        if (isWindows) {
            fileDAO.unzipFile(new ZipFile(downloadedFile), downloadFolder.getParentFile());
        } else {
            fileDAO.unGzipAndUntarFile(new GZIPInputStream(jarURL.openStream()), downloadedFile);
        }

        // get the new jar file
        MavenJarFile newMavenJar;
        if (isWindows) {
            newMavenJar = fileDAO.getMavenJarFileFromFolderWithArtifactId(downloadFolder, artifactId);
        } else {
            newMavenJar = fileDAO.getMavenJarFileFromFolderWithArtifactId(downloadFolder, artifactId);
        }

        // delete the downloaded zip file
        if (cleanupZipFile) {
            if (!downloadedFile.delete()) {
                throw new IOException("could not delete the zip file");
            }
        }

        return newMavenJar;
    }

//    public void run() {
//        MavenJarFile oldMavenJarFile = new MavenJarFile(jarPath.toURI());
//
//        if (WebDAO.newVersionReleased(oldMavenJarFile, downloadURL)) {
//
//            //TL;DR of the next three lines: make the url for the latest version location of a maven jar file
//            String artifactInRepoLocation = new StringBuilder(downloadURL.toExternalForm()).append(oldMavenJarFile.getGroupId().replaceAll("\\.", "/")).append("/").append(oldMavenJarFile.getArtifactId()).toString();
//            String latestRemoteRelease = WebDAO.getLatestVersionNumberFromRemoteRepo(new URL(new StringBuilder(artifactInRepoLocation).append("/maven-metadata.xml").toString()));
//            String latestArtifactLocation = new StringBuilder(artifactInRepoLocation).append("/").append(latestRemoteRelease).toString();
//
//            // download and unzip the files
//            MavenJarFile downloadedJarFile = downloadAndUnzipJar(oldMavenJarFile);
//
//            final File jarParent = new File(jarPath.toURI()).getParentFile();
//
//            // ask if the user really wants to delete the old folder 
////            if (deletePreviousVersion && fileDAO instanceof GUIFileDAO) {
////                int option = JOptionPane.showConfirmDialog(null,
////                        "Remove the old version of " + toolName + "? This will delete the folder\n"
////                        + "" + jarParent.getAbsolutePath(), "Remove Old " + toolName + " Version?", JOptionPane.YES_NO_OPTION);
////
////                if (option != JOptionPane.YES_OPTION) {
////                    deleteOldFiles = false;
////                }
////            }
//           
//
//            // add desktop icon
//            if (createDesktopShortcut) {
//                if (System.getProperty("os.name").toLowerCase(new Locale("en")).contains("win")) {
//                    //try{
//                    fileDAO.createDesktopShortcut(downloadedJarFile, iconName, toolName, deleteOldFiles);
//                    //}catch(IOException ioe){ if (!ignoreShortcutCreationErrors){throw ioe}}
//                } else {
//                    // @TODO: update symlinks?
//                }
//            }
//            try {
//                // close the access to the old zip file so that it can be deleted
//                oldMavenJarFile.close();
//
//                Process launchedJar = null;
//                if (startFileAfterDownload) {
//                    launchedJar = launchJar(downloadedJarFile, args);
//                }
//                if (deletePreviousVersion) {
//                        Runtime.getRuntime().addShutdownHook(new Thread() {
//                            @Override
//                            public void run() {
//                                try {
//                                    if (jarParent.exists()) {
//                                        //dangerous, find better way to do this
//                                        FileUtils.deleteDirectory(jarParent);
//                                    }
//                                } catch (IOException ex) {
//                                    ex.printStackTrace();
//                                    //todo handle stuff did not get done
//                                }
//                            }
//                        });
//                    
//                    if (launchedJar != null) {
//                        launchedJar.waitFor();
//                    }
//                }
//            } catch (InterruptedException ie) {
//                throw new InterruptedIOException("jvm ended unexpectedly, old files have not been deleted");
//            } catch (IOException ex) {
//                Logger.getLogger(DownloadLatestZipFromRepo.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//    }
}
