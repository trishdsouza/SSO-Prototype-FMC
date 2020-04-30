package com.freedommortgage.sso.model;

import java.util.Objects;

public class JWTRequestObj {
    private String grantType;
    private String scope;

    public String getGrantType() {
        return grantType;
    }

    public String getScope() {
        return scope;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JWTRequestObj that = (JWTRequestObj) o;
        return Objects.equals(grantType, that.grantType) &&
                Objects.equals(scope, that.scope);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grantType, scope);
    }

    

    
}
