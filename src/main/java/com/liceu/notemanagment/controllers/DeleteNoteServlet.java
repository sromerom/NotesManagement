package com.liceu.notemanagment.controllers;

import com.liceu.notemanagment.services.NoteService;
import com.liceu.notemanagment.services.NoteServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/delete")
public class DeleteNoteServlet extends HttpServlet {
    private Long noteid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("noteid") != null) {
            HttpSession session = req.getSession();
            NoteService ns = new NoteServiceImpl();
            noteid = Long.parseLong(req.getParameter("noteid"));
            Long userid = (Long) session.getAttribute("userid");

            if (userid != null && noteid != null) {
                boolean noError = ns.deleteNote(userid, noteid);
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }
}
