package com.liceu.notemanagment.controllers;

import com.liceu.notemanagment.services.NoteService;
import com.liceu.notemanagment.services.NoteServiceImpl;
import com.liceu.notemanagment.services.SharedNoteService;
import com.liceu.notemanagment.services.SharedNoteServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/home")
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoteService ns = new NoteServiceImpl();
        SharedNoteService sns = new SharedNoteServiceImpl();

        HttpSession session = req.getSession();
        Long userid = (Long) session.getAttribute("userid");
        String titleFilter = req.getParameter("titleFilter");
        String initDateFilter = req.getParameter("noteStart");
        String endDateFilter = req.getParameter("noteEnd");

        System.out.println(titleFilter);
        System.out.println(initDateFilter);
        System.out.println(endDateFilter);

        if (ns.checkFilter(titleFilter, initDateFilter, endDateFilter)) {
            System.out.println("Aplicamos filter");
            req.setAttribute("notes", ns.filter(userid, titleFilter, initDateFilter, endDateFilter));
        } else {
            System.out.println("No aplicamos filter...");
            req.setAttribute("notes", ns.getNotesFromUser(userid));
        }

        req.setAttribute("sharedNotesWithMe", sns.getSharedNoteWithMe(userid));
        req.setAttribute("sharedNotes", sns.getSharedNotes(userid));
        System.out.println(sns.getSharedNoteWithMe(userid));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }
}