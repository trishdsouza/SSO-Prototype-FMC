package com.freedommortgage.services.signing.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.freedommortgage.services.signing.exception.ApiException;
import com.freedommortgage.services.signing.model.TokenUrl;
import com.freedommortgage.services.signing.service.impl.ApiClientImpl;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/v1")
public class JWTController {

    @Autowired
    private ApiClientImpl api;

    @PreAuthorize("@loanAuthorizationService.isUserAuthorized(#request, principal.username)")
    @PostMapping(value = "/tokenUrl", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.ALL_VALUE)
    @ApiOperation(value = "Getting Authenticated user details", notes = "Validating the user and URL", responseContainer = "array", response = String.class)
    public TokenUrl retrieveAccesstoken(final HttpServletRequest request, final Principal principal, @RequestBody final String customerUrl)
            throws ApiException, IOException, GeneralSecurityException {

        final String token = api.requestJWTUserToken(customerUrl);
        return new TokenUrl(token, customerUrl);

    }

    @GetMapping(value = "/test")
    @ResponseStatus(HttpStatus.OK)
    public String showHello() {

        return "Hello World!";
    }

}
