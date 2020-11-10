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

    private Long idnote = null;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            idnote = Long.parseLong(req.getParameter("id"));
        }
        req.setAttribute("action", "/edit");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userForm.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        NoteService ns = new NoteServiceImpl();
        long iduser = (long) session.getAttribute("userid");
        String title = req.getParameter("title");
        String body = req.getParameter("bodyContent");


        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userForm.jsp");
        dispatcher.forward(req, resp);
    }
}
