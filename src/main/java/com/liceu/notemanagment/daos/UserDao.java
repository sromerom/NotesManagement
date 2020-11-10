package com.liceu.notemanagment.daos;
import com.liceu.notemanagment.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers();

    //public boolean existsUserLogin(String username, String password);
    public User existsUserLogin(String username, String password);

    public User getUserById(long id);

    public void create(User user);

    public void update(User user);

    public void deleteUser(User user);
}