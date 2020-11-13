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

    private final double PAGES_FOR_NOTE = 10.0;
    private Integer currentPage = null;
    private Integer offset = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoteService ns = new NoteServiceImpl();
        SharedNoteService sns = new SharedNoteServiceImpl();

        HttpSession session = req.getSession();
        Long userid = (Long) session.getAttribute("userid");

        //Params filtre
        String typeNoteDisplay = req.getParameter("typeNote");
        String titleFilter = req.getParameter("titleFilter");
        String initDateFilter = req.getParameter("noteStart");
        String endDateFilter = req.getParameter("noteEnd");

        double totalPages = 0;
        if (typeNoteDisplay != null && !typeNoteDisplay.equals("")) {
            if (typeNoteDisplay.equals("compartides")) {
                req.setAttribute("typeNote", typeNoteDisplay);
                req.setAttribute("notes", sns.getSharedNoteWithMe(userid));
                //Cambiar a metode
                totalPages = Math.ceil(sns.getSharedNoteWithMe(userid).size() / PAGES_FOR_NOTE);
            } else if (typeNoteDisplay.equals("compartit")) {
                req.setAttribute("typeNote", typeNoteDisplay);
                req.setAttribute("notes", sns.getSharedNotes(userid));
                totalPages = Math.ceil(sns.getSharedNotes(userid).size() / PAGES_FOR_NOTE);
            } else {
                req.setAttribute("typeNote", typeNoteDisplay);
                req.setAttribute("notes", ns.getNotesFromUser(userid, offset));
                totalPages = Math.ceil(ns.getNotesLength(userid) / PAGES_FOR_NOTE);
            }
        } else {
            typeNoteDisplay = "propies";
            req.setAttribute("typeNote", typeNoteDisplay);
            req.setAttribute("notes", ns.getNotesFromUser(userid, offset));
            totalPages = Math.ceil(ns.getNotesLength(userid) / PAGES_FOR_NOTE);
        }

        //Aplicam filtres
        if (ns.checkFilter(titleFilter, initDateFilter, endDateFilter)) {
            if (!typeNoteDisplay.equals("propies")) {
                System.out.println("Aplicamos filter a shared notes");
                req.setAttribute("notes", sns.filter(userid, titleFilter, initDateFilter, endDateFilter));
            } else {
                System.out.println("Aplicamos filter a created notes");
                req.setAttribute("notes", ns.filter(userid, titleFilter, initDateFilter, endDateFilter));
            }
        }

        //Pagination
        if (req.getParameter("currentPage") != null) {
            currentPage = Integer.parseInt(req.getParameter("currentPage"));
            if (currentPage != 1 || currentPage != 0) {
                String offsetString = (currentPage - 1) + "0";
                offset = Integer.parseInt(offsetString);
            }
        }


        //double totalPages = Math.ceil(ns.getNotesLength(userid) / PAGES_FOR_NOTE);
        System.out.println("total pages: " + totalPages);
        System.out.println("current page: " + currentPage);
        System.out.println("offset: " + offset);

        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);

        //req.setAttribute("sharedNotesWithMe", sns.getSharedNoteWithMe(userid));
        //req.setAttribute("sharedNotes", sns.getSharedNotes(userid));
        //System.out.println(sns.getSharedNoteWithMe(userid));
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }
}