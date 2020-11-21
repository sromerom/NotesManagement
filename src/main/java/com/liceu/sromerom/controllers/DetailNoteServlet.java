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
import java.io.PrintWriter;

@WebServlet(value = "/detail")
public class DetailNoteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            Long noteid = Long.parseLong(req.getParameter("id"));
            NoteService ns = new NoteServiceImpl();
            HttpSession session = req.getSession();
            Long userid = (Long) session.getAttribute("userid");

            req.setAttribute("titleNote", ns.getNoteById(userid, noteid).getTitle());
            req.setAttribute("bodyNote", ns.getParsedBodyToHTML(ns.getNoteById(userid, noteid).getBody()));
        }
        //super.doGet(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
