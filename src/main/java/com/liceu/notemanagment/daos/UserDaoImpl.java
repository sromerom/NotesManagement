package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private List<User> users = new ArrayList<>();

    public UserDaoImpl() {
        try {
            Connection c = Database.getConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM user");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long userid = rs.getLong(1);
                String email = rs.getString(2);
                String username = rs.getString(3);
                String password = rs.getString(4);
                User user = new User(userid, email, username, password);
                this.users.add(user);
            }
            ps.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers(long userid) throws Exception {
        List<User> result = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE NOT user_id = ?");
        ps.setLong(1, userid);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            long actualUserid = rs.getLong(1);
            String email = rs.getString(2);
            String username = rs.getString(3);
            String password = rs.getString(4);
            User user = new User(actualUserid, email, username, password);
            result.add(user);
        }
        return result;
    }

    @Override
    public User getUserById(long id) {
        for (User user : this.users) {
            if (user.getIduser() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean existsUserWithUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public long getUserIdByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user.getIduser();
            }
        }
        return -1;
    }

    @Override
    public void create(User user) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO user (email, username, password) values (?, ?, ?)");
        ps.setString(1, user.getEmail());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getPassword());
        ps.execute();
        ps.close();

    }

    @Override
    public void update(User user) throws Exception {
        Iterator<User> it = this.users.iterator();
        while (it.hasNext()) {
            User u = it.next();
            if (u.getIduser() == user.getIduser()) {
                u.setEmail(user.getEmail());
                u.setUsername(user.getUsername());
                u.setPassword(user.getPassword());
            }
        }
    }

    @Override
    public void deleteUser(User user) throws Exception {
    }
}
