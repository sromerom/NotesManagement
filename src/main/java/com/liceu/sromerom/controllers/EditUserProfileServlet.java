package com.liceu.sromerom.controllers;

import com.liceu.sromerom.services.NoteService;
import com.liceu.sromerom.services.NoteServiceImpl;
import com.liceu.sromerom.services.UserService;
import com.liceu.sromerom.services.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(value = "/editProfile")
public class EditUserProfileServlet extends HttpServlet {
    private Long userid = null;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserService us = new UserServiceImpl();
        HttpSession session = req.getSession();
        userid = (Long) session.getAttribute("userid");

        req.setAttribute("action", "/edit");

        if (userid != null && us.getUserById(userid) != null) {
            req.setAttribute("username", us.getUserById(userid).getUsername());
            req.setAttribute("email", us.getUserById(userid).getEmail());
        } else {
            resp.sendRedirect(req.getContextPath() + "/restrictedArea");
            return;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userProfile.jsp");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        long userid = (long) session.getAttribute("userid");
        String newEmail = req.getParameter("newEmail");
        String newUser = req.getParameter("newUser");
        String currentPassword = req.getParameter("currentPassword");
        String newPass = req.getParameter("newPass");
        String newPassConfirm = req.getParameter("newPassConfirm");
        UserService us = new UserServiceImpl();

        System.out.println(newEmail);
        System.out.println(newUser);
        System.out.println(currentPassword);
        System.out.println(newPass);
        System.out.println(newPassConfirm);

        boolean noError = false;
        if (newEmail == null && newUser == null && currentPassword != null && newPass != null && newPassConfirm != null) {
            boolean validInfo = us.checkPasswordData(userid, currentPassword, newPass, newPassConfirm);
            if (validInfo) {
                noError = us.editPassword(userid, newPass);
            }
        }

        if (currentPassword == null && newPass == null && newPassConfirm == null && newEmail != null && newUser != null) {
            boolean validInfo = us.checkEditData(userid, newEmail, newUser);
            if (validInfo) {
                noError = us.editDataInfo(userid, newEmail, newUser);
            }
        }

        if (noError) {
            System.out.println("S'ha actualitzat correctament...");
            resp.sendRedirect(req.getContextPath() + "/home");
            return;
        }


        System.out.println("No s'ha actualitzat la contraseña correctament...");
        req.setAttribute("noerror", false);
        req.setAttribute("action", "/edit");
        RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/userProfile.jsp");
        dispatcher.forward(req, resp);
    }
}
