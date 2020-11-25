package com.liceu.sromerom.controllers;

import com.liceu.sromerom.services.NoteService;
import com.liceu.sromerom.services.NoteServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/detail")
public class DetailNoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            long noteid = Long.parseLong(req.getParameter("id"));
            NoteService ns = new NoteServiceImpl();
            HttpSession session = req.getSession();
            Long userid = (Long) session.getAttribute("userid");

            //Pasarem els atributs a la vista sempre i quan la nota sigui compartida amb tu
            if (ns.isSharedNote(userid, noteid) || ns.isNoteOwner(userid, noteid)) {
                req.setAttribute("titleNote", ns.getNoteById(noteid).getTitle());
                req.setAttribute("bodyNote", ns.getNoteById(noteid).getBody());
            } else {
                resp.sendRedirect(req.getContextPath() + "/restrictedArea");
                return;
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/detail.jsp");
        dispatcher.forward(req, resp);

    }
}
