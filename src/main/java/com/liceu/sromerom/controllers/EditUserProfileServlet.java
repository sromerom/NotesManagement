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

@WebServlet(value = "/editProfile")
public class EditUserProfileServlet extends HttpServlet {
    private Long userid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService us = new UserServiceImpl();
        HttpSession session = req.getSession();
        userid = (Long) session.getAttribute("userid");

        req.setAttribute("action", "/edit");

        if (userid != null && us.getUserById(userid) != null) {
            req.setAttribute("username", us.getUserById(userid).getUsername());
            req.setAttribute("email", us.getUserById(userid).getEmail());
        } else {
            resp.sendRedirect(req.getContextPath() + "/restrictedArea");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userProfile.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userProfile.jsp");
        dispatcher.forward(req, resp);
    }
}
