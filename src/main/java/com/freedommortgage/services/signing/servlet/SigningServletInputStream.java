package com.freedommortgage.services.signing.servlet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletInputStream;

public class SigningServletInputStream extends ServletInputStream {

    private InputStream requestBodyInputStream;

    public SigningServletInputStream(final byte[] wrappedRequest) {

        this.requestBodyInputStream = new ByteArrayInputStream(wrappedRequest);
    }

    public boolean isFinished() {

        try {
            return requestBodyInputStream.available() == 0;
        } catch (final IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean isReady() {

        return true;
    }

    @Override
    public int read() throws IOException {

        return requestBodyInputStream.read();
    }

}
