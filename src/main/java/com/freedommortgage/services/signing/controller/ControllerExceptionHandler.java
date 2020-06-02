package com.freedommortgage.services.signing.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.freedommortgage.services.signing.exception.ApiException;
import com.freedommortgage.services.signing.exception.ErrorResponse;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public String illegalArgumentException(final HttpServletRequest request, final Exception ex) {

        logger.info("Arguments do not match :: URL=" + request.getRequestURL());
        return "Unauthorized";
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "IOException occured")
    @ExceptionHandler(IOException.class)
    public void handleIOException() {

        logger.error("IOException handler executed");
        // returning 404 error code
    }

    @ExceptionHandler(ApiException.class)
    public final ResponseEntity<ErrorResponse> handleApiException(final ApiException ex, final WebRequest request) {

        final List<String> details = new ArrayList<>();
        details.add("We are sorry that it is taking too long. Can you please re-try...");
        // details.add(ex.getLocalizedMessage());
        logger.error("Unable to connect to Docutech", ex);
        final ErrorResponse error = new ErrorResponse("TRYING TO CONNECT...", details);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
