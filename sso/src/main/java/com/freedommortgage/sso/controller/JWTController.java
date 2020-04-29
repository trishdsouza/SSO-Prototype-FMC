package com.freedommortgage.sso.controller;

import com.freedommortgage.sso.jwtconfig.JWTBuilder;
import com.freedommortgage.sso.model.JWTRequestObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class JWTController {
    
    @Autowired
    private JWTRequestObj jro;
    @Autowired
    private JWTBuilder jwtBuilder;

    @Autowired
    public JWTController(JWTRequestObj jro, JWTBuilder jwtBuilder) {
        this.jro = jro;
        this.jwtBuilder = jwtBuilder;
    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public JWTRequestObj postToken(@RequestBody JWTRequestObj jwt) {
        jro.setAssertion(jwtBuilder.generateJWT());
        return jro;
    }
    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String sampleCheck() {
        return "Hello World!";
    }
    
}
