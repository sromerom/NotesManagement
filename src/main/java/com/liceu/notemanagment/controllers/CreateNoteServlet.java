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

@WebServlet(value = "/create")
public class CreateNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", "/create");
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

        //Cream la nota...
        boolean noError = ns.addNote(iduser, title, body);

        if (noError) {
            System.out.println("S'ha creat la nota correctament...");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        System.out.println("No s'ha creat la nota correctament...");
        req.setAttribute("noerror", noError);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userForm.jsp");
        dispatcher.forward(req, resp);
    }
}
