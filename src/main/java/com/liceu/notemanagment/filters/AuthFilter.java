package com.liceu.notemanagment.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "AuthFilter")
public class AuthFilter implements Filter {
    private static final String[] loginRequiredURLs = new String[]{"/home"};


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
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

        //No ha fet login y a damunt vol entrar en la part privada
        if (username == null && needLogin(req)) {
            System.out.println("No s'ha fet login...");
            //RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
            //dispatcher.forward(servletRequest, servletResponse);
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        //L'usuari ya ha fet login i esta en la pagina login, per tant, ho duim a la pagina principal per que ja esta autenticat i no pinta res en el login
        if (username != null && req.getRequestURI().equals(urlLogin)) {
            System.out.println("No fa falta que tornis al login, estas autenticat!!!");
            //RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
            //dispatcher.forward(servletRequest, servletResponse);
            resp.sendRedirect(req.getContextPath() + "/home");
        }

        //Si s'arriba fins aqui, voldra dir dues coses:
        // 1.- Que l'usuari ha fet login y es troba en una pagina que no es el login.
        // 2.- L'usuari es la primera vegada que entra y no te cap sessio iniciada, per tant en la part del login ho deixarem passar
        filterChain.doFilter(servletRequest, servletResponse);
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
