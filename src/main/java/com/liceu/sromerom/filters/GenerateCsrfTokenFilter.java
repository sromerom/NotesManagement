package com.liceu.sromerom.filters;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@WebFilter(urlPatterns = {"/*"})
public class GenerateCsrfTokenFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();

        Cache<String, Boolean> tokenCache = (Cache<String, Boolean>) session.getAttribute("tokenCache");
        if (tokenCache == null) {
            tokenCache = CacheBuilder.newBuilder()
                    .maximumSize(5000)
                    .expireAfterWrite(60, TimeUnit.MINUTES)
                    .build();
            session.setAttribute("tokenCache", tokenCache);
        }

        String token = UUID.randomUUID().toString();
        tokenCache.put(token, true);
        req.setAttribute("csrfToken", token);
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
