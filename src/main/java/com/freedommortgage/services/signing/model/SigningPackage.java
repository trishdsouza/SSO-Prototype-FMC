package com.freedommortgage.services.signing.model;

public class SigningPackage {

    private String packageURL;

    private String name = "somebody";

    private boolean signingFlag = true;

    private String consentStatus = "unknown";

    public SigningPackage() {

        this.packageURL = "Default_URL";
    }

    public SigningPackage(final String packageURL) {

        this.packageURL = packageURL;
    }

    public final String getName() {

        return name;
    }

    public final void setName(final String name) {

        this.name = name;
    }

    public final boolean isSigningFlag() {

        return signingFlag;
    }

    public final void setSigningFlag(final boolean signingFlag) {

        this.signingFlag = signingFlag;
    }

    public String getPackageURL() {

        return packageURL;
    }

    public void setPackageURL(final String newURL) {

        this.packageURL = newURL;
    }

    public String getConsentStatus() {

        return consentStatus;
    }

    public void setConsentStatus(final String consentStatus) {

        this.consentStatus = consentStatus;
    }

}
