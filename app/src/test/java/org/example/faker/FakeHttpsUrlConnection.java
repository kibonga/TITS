package org.example.faker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

public class FakeHttpsUrlConnection extends HttpsURLConnection {

    public FakeHttpsUrlConnection() {
        super(null);
    }

    @Override
    public int getResponseCode() throws IOException {
        return 200;
    }

    @Override
    public String getResponseMessage() throws IOException {
        return "Success";
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return new ByteArrayOutputStream();
    }

    @Override
    public String getCipherSuite() {
        return "";
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return new Certificate[0];
    }

    @Override
    public Certificate[] getServerCertificates()
        throws SSLPeerUnverifiedException {
        return new Certificate[0];
    }

    @Override
    public void disconnect() {
        // Not implemented yet
    }

    @Override
    public boolean usingProxy() {
        return false;
    }

    @Override
    public void connect() throws IOException {
        // Not implemented yet
    }
}
