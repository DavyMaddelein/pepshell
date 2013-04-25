package com.compomics.partialtryp.controllers.DAO;

import java.io.BufferedInputStream;
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
        String htmlPage;
        URL myURL = new URL(aUrl);
        StringBuilder input = new StringBuilder();
        HttpURLConnection c = (HttpURLConnection) myURL.openConnection();
        Reader r = new InputStreamReader(new BufferedInputStream(c.getInputStream()), "UTF-8");
        CharBuffer buffer = CharBuffer.allocate(256);
        while ((r.read(buffer)) != -1) {
            input.append(buffer.flip());
            buffer.clear();
        }
        r.close();
        htmlPage = input.toString();
        return htmlPage;
    }
}
