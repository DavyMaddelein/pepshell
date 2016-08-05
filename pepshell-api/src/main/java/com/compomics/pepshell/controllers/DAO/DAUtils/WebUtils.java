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

package com.compomics.pepshell.controllers.DAO.DAUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/4/13 Time: 10:17 AM
 * helps with accessing of websites and reducing boilerplate
 */
public class WebUtils {


    /**
     * fetches the html page at the passed url as a string
     * @param aUrl the url to fetch the html page from
     * @return the page at the url in textual form
     * @throws IOException
     */
    public static String getPage(String aUrl) throws IOException {
        StringBuilder input = new StringBuilder();
        String htmlPage;
        try (Reader r = openReader(aUrl)) {
            CharBuffer buffer = CharBuffer.allocate(256);
            while ((r.read(buffer)) != -1) {
                input.append(buffer.flip());
                buffer.clear();
            }
        }
        htmlPage = input.toString();
        return htmlPage;
    }


    /**
     * convenience method for opening a buffered reader to an url, standard timeout is set to 500 milliseconds
     * @param aUrl the url to open a reader to
     * @return the buffered reader for reading the url
     * @throws IOException if the url could not be read
     */
    public static BufferedReader openReader(String aUrl) throws IOException {
        URL myURL = new URL(aUrl);
        HttpURLConnection c = (HttpURLConnection) myURL.openConnection();
        c.setConnectTimeout(500);
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(c.getInputStream()), "UTF-8"));
    }

    public static BufferedInputStream openStream(String aURL) throws IOException {
        URL myURL = new URL(aURL);
        HttpURLConnection c = (HttpURLConnection) myURL.openConnection();
        c.setConnectTimeout(500);
        return new BufferedInputStream(c.getInputStream());

    }
}
