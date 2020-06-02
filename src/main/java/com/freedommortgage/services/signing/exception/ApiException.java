package com.freedommortgage.services.signing.exception;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiException extends RuntimeException {

    // CHECKSTYLE:OFF
    private int code;

    private Map<String, List<String>> responseHeaders;

    private String responseBody;

    // CHECKSTYLE:ON

    public ApiException(final Throwable throwable) {

        super(throwable);
    }

    public ApiException(final String message) {

        super(message);
    }

    public ApiException(final String message, final Throwable throwable, final int code, final Map<String, List<String>> responseHeaders, final String responseBody) {

        super(message, throwable);
        this.code = code;
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    public ApiException(final String message, final int code, final Map<String, List<String>> responseHeaders, final String responseBody) {

        this(message, (Throwable) null, code, responseHeaders, responseBody);
    }

    public ApiException(final String message, final Throwable throwable, final int code, final Map<String, List<String>> responseHeaders) {

        this(message, throwable, code, responseHeaders, null);
    }

    public ApiException(final int code, final Map<String, List<String>> responseHeaders, final String responseBody) {

        this((String) null, (Throwable) null, code, responseHeaders, responseBody);
    }

    public ApiException(final int code, final String message) {

        super(message);
        this.code = code;
    }

    public ApiException(final int code, final String message, final Map<String, List<String>> responseHeaders, final String responseBody) {

        this(code, message);
        this.responseHeaders = responseHeaders;
        this.responseBody = responseBody;
    }

    /**
     * Get the HTTP status code.
     *
     * @return HTTP status code
     */
    public int getCode() {

        return code;
    }

    /**
     * Get the HTTP response headers.
     *
     * @return A map of list of string
     */
    public Map<String, List<String>> getResponseHeaders() {

        return responseHeaders;
    }

    /**
     * Get the HTTP response body.
     *
     * @return Response body in the form of string
     */
    public String getResponseBody() {

        return responseBody;
    }

}
