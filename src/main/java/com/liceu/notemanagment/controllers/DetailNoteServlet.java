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
import java.io.PrintWriter;

@WebServlet(value = "/detail")
public class DetailNoteServlet extends HttpServlet {

    private Long noteid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("id") != null) {
            noteid = Long.parseLong(req.getParameter("id"));
            NoteService ns = new NoteServiceImpl();
            //req.setAttribute("noteid", noteid);
            //req.setAttribute("title", ns.getTitleById(noteid));
            //req.setAttribute("body", ns.getBodyById(noteid));
            req.setAttribute("note", ns.getNoteById(noteid));

            resp.setContentType("text/html");
            PrintWriter out = resp.getWriter();
            out.print("<html>");
            out.print("<head><title></title></head>");
            out.print("<body>");
            out.print("<h1>" + ns.getNoteById(noteid).getTitle() + "</h1>");
            out.print(ns.getParsedBodyNote(ns.getNoteById(noteid).getBody()));
            out.print("</body>");
            out.print("</html>");
        }
        //super.doGet(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
