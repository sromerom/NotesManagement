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

@WebServlet(value = "/delete")
public class DeleteNoteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getParameterValues("checkboxDelete") != null) {
            HttpSession session = req.getSession();
            NoteService ns = new NoteServiceImpl();
            String[] idsToDelete = req.getParameterValues("checkboxDelete");
            Long userid = (Long) session.getAttribute("userid");

            //Eliminarem notes sempre i quan tinguem un id d'usuari i tinguem notes per eliminar
            if (userid != null && idsToDelete.length > 0) {
                ns.deleteNote(userid, idsToDelete);
                resp.sendRedirect(req.getContextPath() + "/home");
            } else {
                resp.sendRedirect(req.getContextPath() + "/restrictedArea");
            }
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/home.jsp");
        dispatcher.forward(req, resp);
    }
}
