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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            noteid = Long.parseLong(req.getParameter("id"));
            NoteService ns = new NoteServiceImpl();
            req.setAttribute("action", "/edit");
            req.setAttribute("noteid", noteid);
            req.setAttribute("title", ns.getTitleById(noteid));
            req.setAttribute("body", ns.getBodyById(noteid));
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userForm.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        NoteService ns = new NoteServiceImpl();
        String title = req.getParameter("title");
        String body = req.getParameter("bodyContent");

        //Actualitzam la nota...
        boolean noError = ns.editNote(noteid, title, body);

        if (noError) {
            System.out.println("S'ha actualitzat la nota correctament...");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userForm.jsp");
        dispatcher.forward(req, resp);
    }
}
