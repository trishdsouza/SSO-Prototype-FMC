package com.freedommortgage.sso.jwtImpl;

import com.freedommortgage.sso.jwtconfig.JWTBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties("jwt")
public class JWTImpl {

    private String grantType;

    private String scope1;

    private String scope2;

    private JWTBuilder jwtBuilder;

    private List<String> scopes = new ArrayList<>();

    public String getGrantType() {
        return grantType;
    }

    public String getScope1() {
        return scope1;
    }

    public String getScope2() {
        return scope2;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setScope1(String scope1) {
        this.scope1 = scope1;
    }

    public void setScope2(String scope2) {
        this.scope2 = scope2;
    }

    public List<String> buildScopes() {
        scopes.add(getScope1());
        scopes.add(getScope2());
        return scopes;
    }

    public String concatenateScopes(){
        return getScope1()+", "+getScope2();
    }

    public String getToken() {
        return jwtBuilder.generateJWT();
    }

}
