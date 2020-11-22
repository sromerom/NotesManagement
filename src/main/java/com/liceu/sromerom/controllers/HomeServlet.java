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

    private final double PAGES_FOR_NOTE = 10.0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        NoteService ns = new NoteServiceImpl();
        UserService us = new UserServiceImpl();
        HttpSession session = req.getSession();

        Long userid = (Long) session.getAttribute("userid");
        req.setAttribute("usernameSession", us.getUserById(userid));
        req.setAttribute("useridSession", userid);
        Integer offset = 0;
        Integer currentPage = 1;
        int totalPages;

        //Params filtres
        String typeNoteDisplay = req.getParameter("typeNote");
        String titleFilter = req.getParameter("titleFilter");
        req.setAttribute("titleFilter", titleFilter);
        String initDateFilter = req.getParameter("noteStart");
        req.setAttribute("initDate", initDateFilter);
        String endDateFilter = req.getParameter("noteEnd");
        req.setAttribute("endDate", endDateFilter);

        System.out.println("type: " + typeNoteDisplay);

        //Pagination
        String filterURL = Filter.getURLFilter(typeNoteDisplay, titleFilter, initDateFilter, endDateFilter);
        System.out.println("Filter URLLLLLLLLLLLLLLLLLLLLLLLL: " + filterURL);
        req.setAttribute("filterURL", filterURL);
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
        //Filter per mostrar nomes notes en especific
        if (typeNoteDisplay != null && !typeNoteDisplay.equals("")) {
            if (typeNoteDisplay.equals("compartides")) {
                req.setAttribute("typeNote", typeNoteDisplay);
                req.setAttribute("notes", ns.getSharedNoteWithMe(userid, offset));
                //Cambiar a metode
                //totalPages = (int) Math.ceil(sns.getSharedNoteWithMe(userid, offset).size() / PAGES_FOR_NOTE);
                totalPages = (int) Math.ceil(ns.getLengthSharedNoteWithMe(userid) / PAGES_FOR_NOTE);
            } else if (typeNoteDisplay.equals("compartit")) {
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
            System.out.println(ns.getNotesFromUser(userid, offset));
            req.setAttribute("notes", ns.getNotesFromUser(userid, offset));
            totalPages = (int) Math.ceil(ns.getAllNotesLength(userid) / (PAGES_FOR_NOTE));
        }

        //Aplicam filtres
        if (ns.checkFilter(titleFilter, initDateFilter, endDateFilter)) {
            System.out.println("Aplicamos filter");
            req.setAttribute("notes", ns.filter(userid, typeNoteDisplay, titleFilter, initDateFilter, endDateFilter, offset));
            totalPages = (int) Math.ceil(ns.filter(userid, typeNoteDisplay, titleFilter, initDateFilter, endDateFilter, offset).size() / (PAGES_FOR_NOTE));
        }

        System.out.println("current page: " + currentPage);
        System.out.println("totalPages " + totalPages);
        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", currentPage);
        req.setAttribute("totalPages", totalPages);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }
}