package com.liceu.notemanagment.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {
    private static final String[] loginRequiredURLs = new String[]{"/main"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = ((HttpServletRequest) servletRequest).getSession();

        String urlLogin = req.getContextPath() + "/login";

        /*
        //Si ja ha fet login anterioment, i torna a fer login, redirigim l'usuari cap a la pagina principal.
        if (session != null && username != null && req.getRequestURI().equals(urlLogin) || req.getRequestURI().endsWith("login.jsp")) {
            req.getRequestDispatcher("/").forward(servletRequest, servletResponse);
            return;
        }
        */

        String username = (String) session.getAttribute("username");
        System.out.println("url login: " + urlLogin);

        //L'usuari ya ha fet login y vol tornar a fer login, per tant, ho duim a la pagina principal
        if (username != null && req.getRequestURI().equals(urlLogin)) {
            RequestDispatcher dispatcher = req.getRequestDispatcher(urlLogin);
            dispatcher.forward(servletRequest, servletResponse);
        }
        //L'usuari a fet login, per tant el deixam entrar
        if (username != null) {
            System.out.println("S'ha fet logim! Tens la sessio iniciada!!");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        //No ha fet login pero l'usuari es troba en la pagina login
        if (username == null && urlLogin.contains("/login")) {
            System.out.println("No tens la sessio iniciada pero et deixo que puguis fer login...");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //No ha fet login y a damunt vol entrar en la part privada
        if (username == null && needLogin(req)) {
            System.out.println("No s'ha fet login...");
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            dispatcher.forward(servletRequest, servletResponse);
            return;
        }
    }

    @Override
    public void destroy() {

    }

    private boolean needLogin(HttpServletRequest req) {
        String actualURL = req.getRequestURL().toString();

        for (String loginRequiredURL : loginRequiredURLs) {
            if (actualURL.contains(loginRequiredURL)) {
                return true;
            }
        }

        return false;
    }
}
