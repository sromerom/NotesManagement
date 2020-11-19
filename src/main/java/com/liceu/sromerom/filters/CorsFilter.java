package com.liceu.sromerom.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter")
public class CorsFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        resp.addHeader("Access-Control-Allow-Origin", "http://localhost:8080");
        resp.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS, HEAD, PUT, POST, DELETE");
        resp.addHeader("Access-Control-Allow-Crentials", "true");
        resp.addHeader("Access-Control-Allow-HEaders", "Content-type");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        if (req.getMethod().equals("OPTIONS")) { //Per saber si el servidor autoritza la peteció
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
