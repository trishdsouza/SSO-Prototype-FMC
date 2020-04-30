package com.freedommortgage.sso.controller;

import com.freedommortgage.sso.jwtconfig.JWTBuilder;
import com.freedommortgage.sso.model.JWTRequestObj;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class JWTController {
    
    private JWTBuilder jwtBuilder;

    @RequestMapping(value = "/token", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public String postToken(JWTRequestObj jro) {

        String token = jwtBuilder.generateJWT();
        return token;
    }

    @GetMapping("/token/success")
    public ResponseEntity<String> getSuccess() {
        return new ResponseEntity<String>(jwtBuilder.generateJWT(), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String sampleCheck() {
        return "Hello World!";
    }
    
}
