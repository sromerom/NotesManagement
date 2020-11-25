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

@WebServlet(value = "/deleteAllShare")
public class DeleteShareServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Si tenim la nota que vol compartir i el id d'usuari, procedirem a compartir la nota
        if (req.getParameter("noteid") != null && session.getAttribute("userid") != null) {
            NoteService ns = new NoteServiceImpl();
            long userid = (long) session.getAttribute("userid");
            long noteid = Long.parseLong(req.getParameter("noteid"));

            System.out.println(ns.isNoteOwner(userid, noteid));
            System.out.println(ns.isSharedNote(userid, noteid));
            if (!ns.isSharedNote(userid, noteid) && !ns.isNoteOwner(userid, noteid)) {
                resp.sendRedirect(req.getContextPath() + "/restrictedArea");
                return;
            }


            //Aconseguim el id de share que es,per poder eliminar aquest
            long sharedNoteId = ns.getSharedNoteId(noteid);


            //Nomes eliminarem, sempre i quan tinguem el shareNoteId
            if (sharedNoteId != -1) {
                ns.deleteAllShareNote(userid, noteid);
            }
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }
}
