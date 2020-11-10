package com.liceu.notemanagment.services;

import com.liceu.notemanagment.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    boolean createUser(String email, String username, String password);
    //boolean existsUserLogin(String username, String password);
    User existsUserLogin(String username, String password);

    long getIdByUser(User user);
}
