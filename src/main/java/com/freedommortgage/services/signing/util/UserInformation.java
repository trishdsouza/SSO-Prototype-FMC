package com.freedommortgage.services.signing.util;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.freedommortgage.services.signing.exception.ApiException;
import com.freedommortgage.services.signing.model.UserProfile;
import com.freedommortgage.services.signing.service.impl.ApiClientImpl;
import com.freedommortgage.servicing.dao.PmuPortalUserDAO;
import com.freedommortgage.servicing.entity.PmuPortalUser;

public class UserInformation {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApiClientImpl api;

    public String getAccessToken(final String url) throws ApiException, IOException, GeneralSecurityException {

        return api.requestJWTUserToken(url);
    }

    public List<String> getUsernameAndSsn(final HttpServletRequest request) {

        String username = null;
        final List<String> userDetails = new ArrayList<String>();
        if (request.getUserPrincipal() != null) {
            username = request.getUserPrincipal().getName();
            final UserProfile userProfile = getUserProfile(username);
            userDetails.add(username);
            final String decryptedSsn = decryptData(userProfile.getEncryptedSsn());
            if (StringUtils.isNotBlank(decryptedSsn)) {
                userDetails.add(decryptedSsn);
            }
        }
        System.out.println(userDetails);
        return userDetails;
    }

    public UserProfile getUserProfile(final String username) {

        try {
            final List<PmuPortalUser> profileList = PmuPortalUserDAO.getPmuPortalUserId(username);

            if (!profileList.isEmpty()) {
                final Integer active = profileList.get(0).getActive();
                if (active.equals(Integer.valueOf(1))) {
                    final String encryptedSsn = profileList.get(0).getSsn();
                    final String email = profileList.get(0).getEmail();
                    final String firstName = profileList.get(0).getFirstName();
                    final String lastName = profileList.get(0).getLastName();
                    final Date lastLoginDate = profileList.get(0).getLastLoginDate();
                    return new UserProfile(firstName, lastName, email, encryptedSsn, lastLoginDate);
                }
            }
        } catch (final Exception ex) {
            logger.error("Blank", ex);
        }
        return null;
    }

    private String decryptData(final String data) {

        final TEA tea = new TEA();
        return tea.decrypt(data);
    }

}
