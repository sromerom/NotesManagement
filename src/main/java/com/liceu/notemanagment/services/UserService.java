package com.liceu.notemanagment.services;

import com.liceu.notemanagment.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll(long userid);
    boolean createUser(String email, String username, String password);
    //boolean existsUserLogin(String username, String password);
    boolean validateUser(String username, String password);
    boolean checkRegister(String email, String username, String password, String password2);
    long getUserId(String username);
    long getIdByUser(User user);
}
