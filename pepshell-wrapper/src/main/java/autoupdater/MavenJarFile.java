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
import java.io.IOException;
import java.net.URI;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * implementation of jar file to handle the niceties maven adds to a jar
 *
 * @author Davy Maddelein
 */
public class MavenJarFile extends JarFile {

    private Properties mavenProperties = new Properties();

    private String absoluteFilePath;

    private URI jarPath;

    /**
     * Create a new MavenJarFile object.
     *
     * @param jarPath the path to the jar file
     * @throws IOException
     */
    public MavenJarFile(URI jarPath) throws IOException {
        super(new File(jarPath));
        this.jarPath = jarPath;
        this.absoluteFilePath = new File(jarPath).getAbsolutePath();
        Enumeration<JarEntry> entries = this.entries();
        //no cleaner way to do this without asking for the group and artifact id, which defeats the point
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.getName().contains("pom.properties")) {
                mavenProperties.load(this.getInputStream(entry));
                break;
            }
        }
    }

    public MavenJarFile(File aJarFile) throws IOException {
        this(aJarFile.toURI());
    }

    public String getArtifactId() {
        return mavenProperties.getProperty("artifactId");
    }

    public String getGroupId() {
        return mavenProperties.getProperty("groupId");
    }
    
    public String getVersionNumber() {
        return mavenProperties.getProperty("version");
    }
    
    public String getAbsoluteFilePath() {
        return absoluteFilePath;
    }

    public URI getJarPath() {
        return jarPath;
    }
}
