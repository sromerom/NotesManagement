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

@WebServlet(value = "/users")
public class UsersServlet extends HttpServlet {
    private Long noteid = null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            noteid = Long.parseLong(req.getParameter("id"));
        }
        UserService us = new UserServiceImpl();
        HttpSession session = req.getSession();
        req.setAttribute("users", us.getAll());
        req.setAttribute("noteid", noteid);
        //req.setAttribute("notes", ns.getNotesFromUser((long) session.getAttribute("userid")));
        System.out.println(us.getAll());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/users.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/users.jsp");
        dispatcher.forward(req, resp);
    }
}
