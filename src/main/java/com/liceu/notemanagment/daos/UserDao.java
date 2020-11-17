package com.liceu.notemanagment.daos;
import com.liceu.notemanagment.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers(long userid) throws Exception;

    public User getUserById(long id);

    public boolean existsUserWithUsername(String username);
    public long getUserIdByUsername(String username);

    public void create(User user) throws Exception;

    public void update(User user) throws Exception;

    public void deleteUser(User user) throws Exception;
}