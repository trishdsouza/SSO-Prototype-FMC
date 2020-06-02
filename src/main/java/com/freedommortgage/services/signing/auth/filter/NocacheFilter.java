package com.freedommortgage.services.signing.auth.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.freedommortgage.services.signing.servlet.SigningServletRequestWrapper;

public class NocacheFilter implements Filter {

    public void doFilter(final ServletRequest request,
            final ServletResponse response,
            final FilterChain chain) throws IOException, ServletException {

        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final SigningServletRequestWrapper requestWrapper = new SigningServletRequestWrapper(httpRequest);
        final HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setDateHeader("Expires", 0);

        chain.doFilter(requestWrapper, response);
    }

    public void destroy() {

        /** Destroy */
    }

    public void init(final FilterConfig fConfig) throws ServletException {

        /** Initialize */
    }
}
