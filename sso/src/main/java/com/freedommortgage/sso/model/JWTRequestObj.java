package com.freedommortgage.sso.model;

import java.util.List;

public class JWTRequestObj {
    
    private String grantType;
    private List<String> scope;
    private String assertion;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public String getAssertion() {
        return assertion;
    }

    public void setAssertion(String assertion) {
        this.assertion = assertion;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        JWTRequestObj that = (JWTRequestObj) o;
//        return Objects.equals(grantType, that.grantType) &&
//                Objects.equals(scope, that.scope) &&
//                Objects.equals(assertion, that.assertion);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(grantType, scope, assertion);
//    }
}
