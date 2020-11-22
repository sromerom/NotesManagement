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

@WebServlet(value = "/deleteShare")
public class DeleteSpecificShareServlet extends HttpServlet {
    private Long noteid = null;
    private Long userid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService us = new UserServiceImpl();
        NoteService ns = new NoteServiceImpl();
        if (req.getParameter("id") != null) {
            noteid = Long.parseLong(req.getParameter("id"));
            HttpSession session = req.getSession();
            userid = (Long) session.getAttribute("userid");
            req.setAttribute("users", us.getAll(userid));
            req.setAttribute("usersShared", us.getSharedUsers(noteid));
            if (ns.getNoteById(userid, noteid) == null) {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/restrictedArea");
            return;
        }
        req.setAttribute("noteid", noteid);
        req.setAttribute("action", "/deleteShare");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/users.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] sharedUsers = req.getParameterValues("states[]");
        NoteService ns = new NoteServiceImpl();
        UserService us = new UserServiceImpl();
        boolean noError = false;

        if (sharedUsers != null && us.existsUserShare(noteid, sharedUsers)) {
            noError = ns.deleteShareNote(userid, noteid, sharedUsers);
        }

        if (noError) {
            System.out.println("S'ha compartit la nota correctament...");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        req.setAttribute("noerror", false);
        req.setAttribute("action", "/deleteShare");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/users.jsp");
        dispatcher.forward(req, resp);

    }
}
