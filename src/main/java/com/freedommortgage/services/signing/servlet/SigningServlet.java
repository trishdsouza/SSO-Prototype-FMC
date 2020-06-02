package com.freedommortgage.services.signing.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import com.freedommortgage.services.signing.model.TokenUrl;
import com.freedommortgage.services.signing.service.impl.ApiClientImpl;
import com.freedommortgage.services.signing.util.UserInformation;

public class SigningServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(SigningServlet.class);

    @Autowired
    private ApiClientImpl api;

    public void initServlet(final ServletConfig config) throws ServletException {

        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());

        logger.info("SSO Servlet " + getServletName() + " has started");

    }

    @Override
    public void destroy() {

        logger.info("SSO Servlet " + getServletName() + " has stopped");

    }

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
            throws ServletException, IOException {

        try {
            logger.debug("SSOServlet method=get, Forwarding request to doPost for processing");
            doPost(request, response);
        } catch (final Exception ex) {
            logger.error("Servlet Exception", ex);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {

        try {
            final UserInformation ui = new UserInformation();
            final List<String> usernameSsn = ui.getUsernameAndSsn(request);
            final String username = usernameSsn.get(0);
            final String ssn = usernameSsn.get(1);
            if (username != null && ssn != null) {

                final SigningServletRequestWrapper requestWrapper = new SigningServletRequestWrapper(request);
                final InputStream in = requestWrapper.getInputStream();
                final byte[] body = StreamUtils.copyToByteArray(in);

                final String url = new String(body);

                final String token = api.requestJWTUserToken(url);
                final TokenUrl tokenUrl = new TokenUrl(token, url);

                final PrintWriter out = response.getWriter();
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                out.print(tokenUrl);
                out.flush();

            }

        } catch (final Exception ex) {
            ex.printStackTrace();
            logger.error("Servlet Exception", ex);
        }
    }

}