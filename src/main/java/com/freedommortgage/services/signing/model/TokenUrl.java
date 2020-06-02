package com.freedommortgage.services.signing.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenUrl {

    @JsonProperty("accessToken")
    private String token;

    @JsonProperty("eSignUrl")
    private String url;

    public String getToken() {

        return token;
    }

    public String getUrl() {

        return url;
    }

    public TokenUrl(final String token, final String url) {

        this.token = token;
        this.url = url;
    }

}
