package com.liceu.notemanagment.daos;
import com.liceu.notemanagment.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();

    public boolean existsUserLogin(String username, String password);

    public User getUserById(long id);

    public boolean create(User user);

    public boolean update(User user);

    public boolean deleteUser(User user);
}