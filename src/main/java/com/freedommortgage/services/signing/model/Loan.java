package com.freedommortgage.services.signing.model;

import java.util.ArrayList;
import java.util.List;

public class Loan {

    private String loanNumber = "12345";

    private String addressStreet = "123 Main St.";

    private List<SigningPackage> packages;

    public Loan() {

        this.packages = new ArrayList<>();
    }

    public String getLoanNumber() {

        return loanNumber;
    }

    public void setLoanNumber(final String loanNumber) {

        this.loanNumber = loanNumber;
    }

    public String getAddressStreet() {

        return addressStreet;
    }

    public void setAddressStreet(final String addressStreet) {

        this.addressStreet = addressStreet;
    }

    public List<SigningPackage> getPackages() {

        return packages;
    }

    public void setPackages(final List<SigningPackage> packages) {

        this.packages = packages;
    }
}
