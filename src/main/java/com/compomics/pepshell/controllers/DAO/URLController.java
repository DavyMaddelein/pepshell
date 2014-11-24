package com.compomics.pepshell.controllers.DAO;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.CharBuffer;

/**
 * Created with IntelliJ IDEA. User: Davy Date: 3/4/13 Time: 10:17 AM To change
 * this template use File | Settings | File Templates.
 */
public class URLController {

    public static String readUrl(String aUrl) throws IOException {
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

    public static BufferedReader openReader(String aUrl) throws IOException {
        URL myURL = new URL(aUrl);
        HttpURLConnection c = (HttpURLConnection) myURL.openConnection();
        c.setConnectTimeout(500);
        return new BufferedReader(new InputStreamReader(new BufferedInputStream(c.getInputStream()), "UTF-8"));
    }
}
