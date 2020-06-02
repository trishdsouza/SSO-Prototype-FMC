package com.freedommortgage.services.signing.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OAuthToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private Long expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    @JsonProperty("scope")
    private String scope;

    @ApiModelProperty(example = "null", value = "")
    public String getAccessToken() {

        return accessToken;
    }

    @ApiModelProperty(example = "3600L", value = "0L")
    public Long getExpiresIn() {

        return expiresIn;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getTokenType() {

        return tokenType;
    }

    @ApiModelProperty(example = "null", value = "")
    public String getScope() {

        return scope;
    }

    @Override
    public int hashCode() {

        return Objects.hash(accessToken, expiresIn, scope, tokenType);
    }

    @Override
    public boolean equals(final java.lang.Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final OAuthToken oAuthToken = (OAuthToken) o;
        return Objects.equals(this.accessToken, oAuthToken.accessToken)
                && Objects.equals(this.expiresIn, oAuthToken.expiresIn)
                && Objects.equals(this.scope, oAuthToken.scope)
                && Objects.equals(this.tokenType, oAuthToken.tokenType);

    }

    @Override
    public String toString() {

        return "OAuthToken [accessToken=" + accessToken + ", expiresIn=" + expiresIn + ", tokenType=" + tokenType + ", scope=" + scope + "]";
    }
}
