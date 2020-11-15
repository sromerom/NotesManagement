package com.liceu.notemanagment.controllers;

import com.liceu.notemanagment.services.NoteService;
import com.liceu.notemanagment.services.NoteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/delete")
public class DeleteNoteServlet extends HttpServlet {
    private Long noteid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            noteid = Long.parseLong(req.getParameter("id"));
            NoteService ns = new NoteServiceImpl();
            req.setAttribute("action", "/delete");
            req.setAttribute("noteid", noteid);
            req.setAttribute("title", ns.getNoteById(noteid).getTitle());
            req.setAttribute("body", ns.getNoteById(noteid).getBody());

            boolean noError = ns.deleteNote(noteid);
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
    }

}
