package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.UserDao;
import com.liceu.notemanagment.daos.UserDaoImpl;
import com.liceu.notemanagment.model.User;

import java.util.List;

public class UserServiceImpl implements UserService {
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

    @Override
    public boolean createUser(String email, String username, String password) {
        try {
            UserDao ud = new UserDaoImpl();
            User user = new User(0, email, username, password);
            ud.create(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
