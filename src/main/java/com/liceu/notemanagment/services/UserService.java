package com.liceu.notemanagment.services;

import com.liceu.notemanagment.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    boolean existsUserLogin(String username, String password);
}
