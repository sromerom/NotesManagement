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
import java.util.Arrays;

@WebServlet(value = "/share")
public class ShareNoteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/share.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        //Long userid = (Long) session.getAttribute("userid");
        String [] sharedUsers = req.getParameterValues("share");
        Long noteid = Long.parseLong(req.getParameter("noteid"));
        SharedNoteService sns = new SharedNoteServiceImpl();
        System.out.println(Arrays.toString(sharedUsers));
        System.out.println("Note id: " + noteid);

        boolean noError = sns.shareNote(noteid, sharedUsers);
        if (noError) {
            System.out.println("S'ha compartit la nota correctament...");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }

        System.out.println("No s'ha compartit la nota correctament...");
        req.setAttribute("noerror", noError);
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userForm.jsp");
        dispatcher.forward(req, resp);
    }
}
