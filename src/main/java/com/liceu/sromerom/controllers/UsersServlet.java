package com.liceu.sromerom.controllers;

import com.liceu.sromerom.services.NoteService;
import com.liceu.sromerom.services.NoteServiceImpl;
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

@WebServlet(value = "/users")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService us = new UserServiceImpl();
        NoteService ns = new NoteServiceImpl();
        if (req.getParameter("id") != null) {
            long noteid = Long.parseLong(req.getParameter("id"));
            HttpSession session = req.getSession();
            Long userid = (Long) session.getAttribute("userid");
            req.setAttribute("users", us.getAll(userid));
            req.setAttribute("noteid", noteid);

            if (ns.getNoteById(userid, noteid) == null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/users.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/users.jsp");
        dispatcher.forward(req, resp);
    }
}
