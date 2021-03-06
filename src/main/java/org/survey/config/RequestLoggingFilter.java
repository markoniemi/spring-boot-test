package org.survey.config;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.filter.CommonsRequestLoggingFilter;

public class RequestLoggingFilter extends CommonsRequestLoggingFilter {
    public RequestLoggingFilter() {
        super.setBeforeMessagePrefix("[");
        super.setIncludeQueryString(true);
        super.setIncludePayload(true);
        super.setMaxPayloadLength(64000);
    }

    @Override
    protected void beforeRequest(HttpServletRequest request, String message) {
        super.beforeRequest(request, request.getMethod() + ":" + message);
    }

    @Override
    protected void afterRequest(HttpServletRequest request, String message) {
        // suppress after request logging
    }
}
