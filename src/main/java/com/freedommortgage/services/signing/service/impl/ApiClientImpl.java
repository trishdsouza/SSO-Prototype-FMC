package com.freedommortgage.services.signing.service.impl;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status.Family;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.freedommortgage.services.signing.exception.ApiException;
import com.freedommortgage.services.signing.helper.JWTHelper;
import com.freedommortgage.services.signing.model.OAuthToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.core.util.MultivaluedMapImpl;

@Component
public class ApiClientImpl {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JWTHelper jwtHelper;

    @Value("${jwt.grantType}")
    private String grantType;

    @Value("${jwt.scope1}")
    private String scope1;

    @Value("${jwt.scope2}")
    private String scope2;

    public String formattedScopes() {

        return scope1.concat(" ").concat(scope2);
    }

    /**
     * This method does a post request to the Docutech Auth Server. It exchanges JWT for an access token.
     */

    public String requestJWTUserToken(final String receivedUrl) {

        String token = null;

        try {
            final String assertion = jwtHelper.generateJWT(receivedUrl);

            final MultivaluedMap<String, String> form = new MultivaluedMapImpl();
            form.add("grant_type", grantType);
            form.add("scope", formattedScopes());
            form.add("assertion", assertion);

            final Client client = new Client();
            final WebResource webResource = client.resource("https://stageauth.conformx.com/connect/token");
            final ClientResponse response = webResource
                    .type(MediaType.APPLICATION_FORM_URLENCODED_TYPE)
                    .header("Cache-Control", "no-store")
                    .header("Pragma", "no-cache")
                    .post(ClientResponse.class, form);

            if (response.getStatusInfo().getFamily() != Family.SUCCESSFUL) {
                final String respBody = response.getEntity(String.class);
                throw new ApiException(
                        /** response.getStatusInfo().getStatusCode(), */
                        "Error while requesting server, received a non successful HTTP code " + response.getStatusInfo().getStatusCode() + " with response Body: '" + respBody
                                + "'"
                /** ,response.getHeaders(), respBody */
                );
            }

            final String respBody = response.getEntity(String.class);

            final ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            final OAuthToken oAuthToken = mapper.readValue(respBody, OAuthToken.class);
            if (oAuthToken.getAccessToken() == null || "".equals(oAuthToken.getAccessToken()) || oAuthToken.getExpiresIn() <= 0) {
                throw new ApiException(
                        "Error while requesting access token " + response);
            }

            token = oAuthToken.getAccessToken();

        } catch (final JsonProcessingException e) {
            logger.error("Json Processing error", e);
        }

        return token;
    }
}
