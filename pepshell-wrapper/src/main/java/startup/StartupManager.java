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

package startup;

import autoupdater.DownloadLatestZipFromRepo;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * the entry point to handle everything pre Pepshell start up like automatic updating and usage tracking
 * Created by Davy Maddelein on 15/01/2015.
 */
public class StartupManager {

    public static void main(String[] args) {

        try {
            FileSystem jar = FileSystems.getFileSystem(StartupManager.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            jar.getRootDirectories().forEach(e -> {
                try {
                    Files.walkFileTree(e, new pepshellVisitor());
                }catch (IOException ex){
                    ex.printStackTrace();
                }});
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }


    }

    private static class pepshellVisitor implements FileVisitor<Path> {
        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (file.getFileName().startsWith("pepshell-program")) {

                try {
                    DownloadLatestZipFromRepo.downloadLatestZipFromRepo(file);
                } catch (XMLStreamException e) {
                    e.printStackTrace();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            //we found what we wanted, let the updater handle it from here
            return FileVisitResult.TERMINATE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            //warn user file was locked and could not be updated
            return FileVisitResult.TERMINATE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            return FileVisitResult.CONTINUE;
        }
    }

}
