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

@WebServlet(value = "/edit")
public class EditNoteServlet extends HttpServlet {

    private Long noteid = null;
    private Long userid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            noteid = Long.parseLong(req.getParameter("id"));
            NoteService ns = new NoteServiceImpl();
            HttpSession session = req.getSession();
            userid = (Long) session.getAttribute("userid");

            req.setAttribute("action", "/edit");

            if (noteid != null && ns.getNoteById(userid, noteid) != null) {
                req.setAttribute("noteid", noteid);
                req.setAttribute("title", ns.getNoteById(userid, noteid).getTitle());
                req.setAttribute("body", ns.getNoteById(userid, noteid).getBody());
            } else {
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userForm.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoteService ns = new NoteServiceImpl();

        boolean noError = false;
        if (req.getParameter("title") != null && req.getParameter("bodyContent") != null) {
            String title = req.getParameter("title");
            String body = req.getParameter("bodyContent");

            //Actualitzam la nota...
            noError = ns.editNote(userid, noteid, title, body);
        }

        if (noError) {
            System.out.println("S'ha actualitzat la nota correctament...");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userForm.jsp");
        dispatcher.forward(req, resp);
    }
}
