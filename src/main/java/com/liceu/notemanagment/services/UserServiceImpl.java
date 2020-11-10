package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.UserDao;
import com.liceu.notemanagment.daos.UserDaoImpl;
import com.liceu.notemanagment.model.User;

import java.util.List;

public class UserServiceImpl implements UserService{
    @Override
    public List<User> getAll() {
        UserDao ud = new UserDaoImpl();
        List<User> users = ud.getAllUsers();
        return users;
    }

    @Override
    public boolean existsUserLogin(String username, String password) {
        UserDao ud = new UserDaoImpl();
        return ud.existsUserLogin(username, password);
    }
}
