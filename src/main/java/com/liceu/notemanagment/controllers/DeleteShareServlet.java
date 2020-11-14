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
    //private Long sharedNoteId = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("noteid") != null && req.getParameter("userid") != null) {
            //sharedNoteId = Long.parseLong(req.getParameter("idShareNote"));
            SharedNoteService sns = new SharedNoteServiceImpl();
            System.out.println("noteid: " + Long.parseLong(req.getParameter("noteid")));
            System.out.println("userid: " + Long.parseLong(req.getParameter("userid")));
            long sharedNoteId = sns.getSharedNoteId(Long.parseLong(req.getParameter("noteid")), Long.parseLong(req.getParameter("userid")));
            boolean noError = false;
            System.out.println("shareNoteId: " + sharedNoteId);
            if (sharedNoteId != -1) {
                noError = sns.deleteShareNote(sharedNoteId);
            }
            if (!noError) {
                System.out.println("Error eliminant el share");
            }
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        } else {
            System.out.println("Nop");
        }
    }
}
