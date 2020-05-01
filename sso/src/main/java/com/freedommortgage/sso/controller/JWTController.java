package com.freedommortgage.sso.controller;

import com.freedommortgage.sso.jwtImpl.JWTImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("jwt")
public class JWTController {

    @Autowired
    JWTImpl jwtImpl;

    @PostMapping(value = "/test",produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> postTest(HttpServletResponse response) throws IOException {

        response.setHeader("grant_type", jwtImpl.getGrantType());
        response.setHeader("scope", jwtImpl.concatenateScopes());
        response.setHeader("assertion", jwtImpl.getToken());
//        response.setHeader("Location", "/read");
//        response.setHeader("Refresh", "20; URL=http://localhost:9897/jwt/test/read");
//        response.sendRedirect("/read");
//        RequestDispatcher view = request.getRequestDispatcher("http://localhost:9897/jwt/test/read");
//        view.forward(request,response);

        return new ResponseEntity<String> ("JWT Token sent to Docutech server!", HttpStatus.OK);
    }

    @GetMapping(value = "/read",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> getToken(HttpServletResponse response) throws Exception {
        return new ResponseEntity<String>(response.getContentType(), HttpStatus.OK);
    }

    
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String sampleCheck() {
        return "Hello World!";
    }
    
}
