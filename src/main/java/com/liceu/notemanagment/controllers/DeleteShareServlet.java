package com.liceu.notemanagment.controllers;
import com.liceu.notemanagment.services.SharedNoteService;
import com.liceu.notemanagment.services.SharedNoteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(value = "/deleteShare")
public class DeleteShareServlet extends HttpServlet {
    private Long sharedNoteId = null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("idShareNote") != null) {
            sharedNoteId = Long.parseLong(req.getParameter("idShareNote"));
            SharedNoteService sns = new SharedNoteServiceImpl();
            boolean noError = sns.deleteShareNote(sharedNoteId);

            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
    }
}
