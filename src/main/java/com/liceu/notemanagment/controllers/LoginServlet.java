package com.liceu.notemanagment.controllers;

import com.liceu.notemanagment.services.UserService;
import com.liceu.notemanagment.services.UserServiceImpl;

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
        String user = req.getParameter("username");
        String pass = req.getParameter("password");
        //En un cas normal, s'hauria de comprovar en la ddbb si esta el usuari en concret

        UserService us = new UserServiceImpl();
        boolean exists = us.existsUserLogin(user, pass);

        if (exists) {
            System.out.println("Estas dentro!!");
            req.setAttribute("username", user);
            HttpSession session = req.getSession();
            session.setAttribute("username", user);

            //RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
            //dispatcher.forward(req, resp);
            System.out.println("CONTEXT PATH...: " + req.getContextPath());
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        /*
        if (user.equals("admin") && pass.equals("1234")) {
            System.out.println("Estas dentro!!");
            req.setAttribute("username", user);
            HttpSession session = req.getSession();
            session.setAttribute("username", user);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
            dispatcher.forward(req, resp);
        }
        */

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/login.jsp");
        dispatcher.forward(req, resp);
    }
}
