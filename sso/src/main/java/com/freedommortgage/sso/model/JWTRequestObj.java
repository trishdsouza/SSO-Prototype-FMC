package com.freedommortgage.sso.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class JWTRequestObj {
    private String grantType;
    private List<String> scopes = new ArrayList<>();
    private String token;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JWTRequestObj that = (JWTRequestObj) o;
        return Objects.equals(grantType, that.grantType) &&
                Objects.equals(scopes, that.scopes) &&
                Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grantType, scopes, token);
    }
}
