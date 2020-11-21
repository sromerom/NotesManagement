package com.liceu.sromerom.daos;

import com.liceu.sromerom.model.User;
import com.liceu.sromerom.utils.Database;

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
    public List<User> getUsersFromSharedNote(long noteid) throws Exception {
        List<User> users = new ArrayList<>();
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT user.user_id, email, username, password FROM sharedNote INNER JOIN user ON sharedNote.user_id = user.user_id WHERE note_id = ?");
        ps.setLong(1, noteid);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            long userid = rs.getLong(1);
            String email = rs.getString(2);
            String username = rs.getString(3);
            String password = rs.getString(4);
            users.add(new User(userid, email, username, password));
        }

        ps.close();
        return users;
    }

    @Override
    public User getUserById(long id) {
        for (User user : this.users) {
            if (user.getUserid() == id) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean existsUserWithUsername(String username) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE username = ?");
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public boolean existsUserWithEmail(String email) throws Exception {
        Connection conn = Database.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM user WHERE email = ?");
        ps.setString(1, email);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    @Override
    public long getUserIdByUsername(String username) {
        for (User user : this.users) {
            if (user.getUsername().equals(username)) {
                return user.getUserid();
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
            if (u.getUserid() == user.getUserid()) {
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
