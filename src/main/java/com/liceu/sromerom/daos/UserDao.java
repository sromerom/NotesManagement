package com.liceu.sromerom.daos;
import com.liceu.sromerom.model.User;

import java.util.List;

public interface UserDao {
    public List<User> getAllUsers(long userid) throws Exception;

    public User getUserById(long id);

    public boolean existsUserWithUsername(String username) throws Exception;
    public boolean existsUserWithEmail(String email) throws Exception;
    public long getUserIdByUsername(String username);

    public void create(User user) throws Exception;

    public void update(User user) throws Exception;

    public void deleteUser(User user) throws Exception;
}