package com.liceu.sromerom.controllers;

import com.liceu.sromerom.services.*;
import com.liceu.sromerom.utils.Filter;

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
        HttpSession session = req.getSession();

        //Parametres de l'usuari
        Long userid = (Long) session.getAttribute("userid");
        String username = (String) session.getAttribute("username");
        req.setAttribute("usernameSession", username);
        req.setAttribute("useridSession", userid);

        //Parametres del filtre de cerca
        String typeNoteDisplay = req.getParameter("typeNote");
        String titleFilter = req.getParameter("titleFilter");
        String initDateFilter = req.getParameter("noteStart");
        String endDateFilter = req.getParameter("noteEnd");
        req.setAttribute("titleFilter", titleFilter);
        req.setAttribute("initDate", initDateFilter);
        req.setAttribute("endDate", endDateFilter);


        //Pagination
        final double PAGES_FOR_NOTE = 10.0; //Quantitat de notes que volem per pagina
        int offset = 0; //Offset que l'hi pasarem al query
        int currentPage = 1; //Pagina actual de la paginacio
        int totalPages; //El numero total de pagines que tindra la paginacio, que variara segons el tipus de cerca que facem

        //Url amb els parametres titleFilter, initDate i endDate + typeNote
        String filterURL = Filter.getURLFilter(typeNoteDisplay, titleFilter, initDateFilter, endDateFilter);

        req.setAttribute("filterURL", filterURL);

        //Per aconseguir el offset i la pagina actual
        if (req.getParameter("currentPage") != null) {
            int actualCurrentPage = Integer.parseInt(req.getParameter("currentPage"));
            if (actualCurrentPage > currentPage) {
                offset = (actualCurrentPage - 1) * 10;
                currentPage = actualCurrentPage;
            } else if (actualCurrentPage < currentPage) {
                offset = (actualCurrentPage - 1) * 10;
                currentPage = actualCurrentPage;
            }
        }

        //Filter per mostrar els tipus de nota (totes les notes juntes, notes creades, notes que t'han compartit i notes que has compartit)
        if (typeNoteDisplay != null && !typeNoteDisplay.equals("")) {
            if (typeNoteDisplay.equals("sharedNotesWithMe")) {
                req.setAttribute("typeNote", typeNoteDisplay);
                req.setAttribute("notes", ns.getSharedNoteWithMe(userid, offset));
                totalPages = (int) Math.ceil(ns.getLengthSharedNoteWithMe(userid) / PAGES_FOR_NOTE);
            } else if (typeNoteDisplay.equals("sharedNotesByYou")) {
                req.setAttribute("typeNote", typeNoteDisplay);
                req.setAttribute("notes", ns.getSharedNotes(userid, offset));
                totalPages = (int) Math.ceil(ns.getLengthSharedNotes(userid) / PAGES_FOR_NOTE);
            } else {
                req.setAttribute("typeNote", typeNoteDisplay);
                req.setAttribute("notes", ns.getCreatedNotes(userid, offset));
                totalPages = (int) Math.ceil(ns.getCreatedNotesLength(userid) / PAGES_FOR_NOTE);
            }
        } else {
            req.setAttribute("typeNote", "allNotes");
            req.setAttribute("notes", ns.getNotesFromUser(userid, offset));
            totalPages = (int) Math.ceil(ns.getAllNotesLength(userid) / (PAGES_FOR_NOTE));
        }

        //Aplicam els filtres de cerca ja sigui per date o per search de paraules clau
        if (Filter.checkFilter(titleFilter, initDateFilter, endDateFilter)) {
            req.setAttribute("notes", ns.filter(userid, typeNoteDisplay, titleFilter, initDateFilter, endDateFilter, offset));
            totalPages = (int) Math.ceil(ns.filter(userid, typeNoteDisplay, titleFilter, initDateFilter, endDateFilter, offset).size() / (PAGES_FOR_NOTE));
        }

        //Pasam a la vista tots els parametres corresponents amb la paginacio
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }
}