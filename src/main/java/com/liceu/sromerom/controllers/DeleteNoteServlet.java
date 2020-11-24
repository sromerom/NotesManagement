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
import java.util.Arrays;

@WebServlet(value = "/delete")
public class DeleteNoteServlet extends HttpServlet {
    private Long noteid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/home");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameterValues("checkboxDelete") != null) {
            HttpSession session = req.getSession();
            NoteService ns = new NoteServiceImpl();
            String[] idsToDelete = req.getParameterValues("checkboxDelete");
            System.out.println(Arrays.toString(idsToDelete));
            Long userid = (Long) session.getAttribute("userid");

            if (userid != null && idsToDelete.length > 0) {
                boolean noError = ns.deleteNote(userid, idsToDelete);
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            } else {
                resp.sendRedirect(req.getContextPath() + "/restrictedArea");
                return;
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
        /*
        if (req.getParameter("noteid") != null) {
            HttpSession session = req.getSession();
            NoteService ns = new NoteServiceImpl();
            noteid = Long.parseLong(req.getParameter("noteid"));
            Long userid = (Long) session.getAttribute("userid");

            if (userid != null && noteid != null) {
                boolean noError = ns.deleteNote(userid, noteid);
                resp.sendRedirect(req.getContextPath() + "/home");
                return;
            } else {
                resp.sendRedirect(req.getContextPath() + "/restrictedArea");
                return;
            }
        }
         */
    }
}
