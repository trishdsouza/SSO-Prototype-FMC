package com.freedommortgage.services.signing.exception;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
public class ErrorResponse {

    public ErrorResponse(final String message, final List<String> details) {

        super();
        this.message = message;
        this.details = details;
    }

    private String message;

    private List<String> details;

    public String getMessage() {

        return message;
    }

    public void setMessage(final String message) {

        this.message = message;
    }

    public List<String> getDetails() {

        return details;
    }

    public void setDetails(final List<String> details) {

        this.details = details;
    }

}
