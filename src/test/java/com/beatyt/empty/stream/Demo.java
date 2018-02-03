package com.beatyt.empty.stream;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Unexpected end of file from server
 *
 * ERR_EMPTY_RESPONSE
 */

public class Demo {

    /**
     * Depending on where the mock load balancer is at, you will either see:
     *
     * 200 Hello Worlld
     *
     * or
     *
     * Error unexpected end of file (after a 4 minute wait)
     */
    @Test
    public void test() {
        URL url = null;
        try {
            final int port = 3001;
            url = new URL("http://localhost:" + port);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        HttpURLConnection httpURLConnection;
        InputStream errorStream = null;
        InputStream inputStream = null;
        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(5);
            httpURLConnection.connect();

            // connect
            int responseCode = httpURLConnection.getResponseCode();
            System.out.println(responseCode);

            errorStream = httpURLConnection.getErrorStream();
            inputStream = httpURLConnection.getInputStream();

            String s = IOUtils.toString(inputStream);

            System.out.println(s);

        } catch (IOException e) {
            System.out.println("Error opening the url\n" + e.getMessage());
            try {
                if (errorStream != null) {
                    String s = IOUtils.toString(errorStream);
                    System.out.println(s);
                }
            } catch (IOException e1) {
                System.out.println("Error: printing out the error stream \n" + e1);
            }
        } finally {
            IOUtils.closeQuietly(inputStream);
            IOUtils.closeQuietly(errorStream);
        }
    }
}
