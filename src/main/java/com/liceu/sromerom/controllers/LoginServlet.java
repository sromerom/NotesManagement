package com.liceu.sromerom.controllers;

import com.liceu.sromerom.services.UserService;
import com.liceu.sromerom.services.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService us = new UserServiceImpl();
        String user = req.getParameter("username");
        String pass = req.getParameter("password");

        //Iniciarem sessio sempre i quan els parametres no siguin null i la validacio d'usuari sigui true
        if (user != null && pass != null && us.validateUser(user, pass)) {
            req.setAttribute("username", user);
            HttpSession session = req.getSession();
            session.setAttribute("userid", us.getUserId(user));

            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }


        req.setAttribute("noerror", false);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(req, resp);
    }

}
