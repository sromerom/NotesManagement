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
    private Long userid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoteService ns = new NoteServiceImpl();
        UserService us = new UserServiceImpl();
        Long noteid = null;
        if (req.getParameter("id") != null) {
            noteid = Long.parseLong(req.getParameter("id"));
            HttpSession session = req.getSession();
            userid = (Long) session.getAttribute("userid");
            req.setAttribute("users", us.getAll(userid));
            req.setAttribute("usersShared", us.getSharedUsers(noteid));


            if (!ns.isNoteOwner(userid, noteid)) {
                resp.sendRedirect(req.getContextPath() + "/restrictedArea");
                return;
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
        req.setAttribute("noteid", noteid);
        req.setAttribute("action", "/deleteShare");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/users.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String[] sharedUsers = req.getParameterValues("users[]");
        long noteid = Long.parseLong(req.getParameter("noteid"));
        NoteService ns = new NoteServiceImpl();
        UserService us = new UserServiceImpl();
        boolean noError = false;

        if (!ns.isNoteOwner(userid, noteid)) {
            resp.sendRedirect(req.getContextPath() + "/restrictedArea");
            return;
        }
        //Eliminarem sempre i quan existeixi usuaris a eliminar el share i que existeixi un share amb aquells usuaris
        if (sharedUsers != null && us.existsUserShare(noteid, sharedUsers)) {
            noError = ns.deleteShareNote(userid, noteid, sharedUsers);
        }

        if (noError) {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        req.setAttribute("noerror", false);
        req.setAttribute("action", "/deleteShare");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/users.jsp");
        dispatcher.forward(req, resp);

    }
}
