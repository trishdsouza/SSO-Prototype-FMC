package com.freedommortgage.services.signing.model;

import java.util.Date;

public class UserProfile {

    private String firstName;

    private String lastName;

    private String email;

    private String encryptedSsn;

    private Date lastLoginDate;

    public UserProfile(final String firstName, final String lastName, final String email, final String encryptedSsn, final Date lastLoginDate) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.encryptedSsn = encryptedSsn;
        this.lastLoginDate = lastLoginDate;
    }

    public String getFirstName() {

        return firstName;
    }

    public String getLastName() {

        return lastName;
    }

    public String getEmail() {

        return email;
    }

    public String getEncryptedSsn() {

        return encryptedSsn;
    }

    public Date getLastLoginDate() {

        return lastLoginDate;
    }

    @Override
    public String toString() {

        return "UserProfile [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", encryptedSsn=" + encryptedSsn + ", lastLoginDate=" + lastLoginDate + "]";
    }

}
