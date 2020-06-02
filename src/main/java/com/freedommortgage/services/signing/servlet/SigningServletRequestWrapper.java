package com.freedommortgage.services.signing.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

public class SigningServletRequestWrapper extends HttpServletRequestWrapper {

    private final byte[] wrappedRequest;

    public SigningServletRequestWrapper(final HttpServletRequest request) throws IOException {

        super(request);
        final InputStream requestInputStream = request.getInputStream();
        this.wrappedRequest = StreamUtils.copyToByteArray(requestInputStream);

    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        return new SigningServletInputStream(this.wrappedRequest);
    }

    @Override
    public BufferedReader getReader() throws IOException {

        final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.wrappedRequest);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    // private final String body;
    //
    // private static final char BYTE_NUM = 128;
    //
    // public SigningServletRequestWrapper(final HttpServletRequest request) throws IOException {
    //
    // // So that other request method behave just like before
    // super(request);
    //
    // final StringBuilder stringBuilder = new StringBuilder();
    // BufferedReader bufferedReader = null;
    // try {
    // final InputStream inputStream = request.getInputStream();
    // if (inputStream != null) {
    // bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    // final char[] charBuffer = new char[BYTE_NUM];
    // int bytesRead = -1;
    // while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
    // stringBuilder.append(charBuffer, 0, bytesRead);
    // }
    // } else {
    // stringBuilder.append("");
    // }
    // } catch (final IOException ex) {
    // throw ex;
    // } finally {
    // if (bufferedReader != null) {
    // try {
    // bufferedReader.close();
    // } catch (final IOException ex) {
    // throw ex;
    // }
    // }
    // }
    // // Store request pody content in 'body' variable
    // body = stringBuilder.toString();
    // }
    //
    // @Override
    // public ServletInputStream getInputStream() throws IOException {
    //
    // final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
    // final ServletInputStream servletInputStream = new ServletInputStream() {
    //
    // public int read() throws IOException {
    //
    // return byteArrayInputStream.read();
    // }
    // };
    // return servletInputStream;
    // }
    //
    // @Override
    // public BufferedReader getReader() throws IOException {
    //
    // return new BufferedReader(new InputStreamReader(this.getInputStream()));
    // }
    //
    // public String getBody() {
    //
    // return this.body;
    // }

}
