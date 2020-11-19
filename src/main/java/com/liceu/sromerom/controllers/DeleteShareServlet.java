package com.liceu.sromerom.controllers;

import com.liceu.sromerom.services.NoteService;
import com.liceu.sromerom.services.NoteServiceImpl;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/deleteShare")
public class DeleteShareServlet extends HttpServlet {
    //private Long sharedNoteId = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (req.getParameter("noteid") != null && session.getAttribute("userid") != null) {
            NoteService ns = new NoteServiceImpl();
            long userid = (long) session.getAttribute("userid");
            long noteid = Long.parseLong(req.getParameter("noteid"));
            //long sharedNoteId = sns.getSharedNoteId(Long.parseLong(req.getParameter("noteid")), Long.parseLong(req.getParameter("userid")));
            long sharedNoteId = ns.getSharedNoteId(noteid);
            boolean noError = false;
            System.out.println("shareNoteId: " + sharedNoteId);
            if (sharedNoteId != -1) {
                noError = ns.deleteShareNote(userid, noteid, sharedNoteId);
            }
            if (!noError) {
                System.out.println("Error eliminant el share");
            }
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }
    }
}
