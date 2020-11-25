package com.liceu.sromerom.filters;

import com.google.common.cache.Cache;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CheckCsrfTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();

        if (req.getMethod().equalsIgnoreCase("POST")) {
            String tokenFromRequest = req.getParameter("_csrftoken");
            Cache<String, Boolean> tokenCache = (Cache<String, Boolean>) session.getAttribute("tokenCache");
            if ((tokenCache == null) || (tokenCache.getIfPresent(tokenFromRequest) == null)) {
                resp.sendRedirect(req.getContextPath() + "/home");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
