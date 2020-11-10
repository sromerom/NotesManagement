package com.liceu.notemanagment.daos;
import com.liceu.notemanagment.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public List<User> getAllUsers() {
        List<User> result = new ArrayList<>();
        try {
            //UserDao userDao = new UserDaoImpl();

            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM user");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long userid = rs.getLong(1);
                String email = rs.getString(2);
                String username = rs.getString(3);
                String password = rs.getString(4);
                User user = new User(userid, email, username, password);
                result.add(user);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean existsUserLogin(String username, String password) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getUserById(long id) {
        return null;
    }

    @Override
    public boolean create(User user) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }
}
