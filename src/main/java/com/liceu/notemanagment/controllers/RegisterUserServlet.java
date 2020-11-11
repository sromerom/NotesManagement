package com.liceu.notemanagment.controllers;

import com.liceu.notemanagment.services.UserService;
import com.liceu.notemanagment.services.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/register")
public class RegisterUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newEmail = req.getParameter("newEmail");
        String newUser = req.getParameter("newUser");
        String newPass = req.getParameter("newPass");

        UserService us = new UserServiceImpl();
        boolean noError = us.createUser(newEmail, newUser, newPass);

        if (noError) {
            System.out.println("S'ha creat correctament");
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.setAttribute("noerror", noError);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/register.jsp");
        dispatcher.forward(req, resp);

    }
}
