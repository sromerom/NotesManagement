package com.liceu.sromerom.services;

import com.liceu.sromerom.daos.UserDao;
import com.liceu.sromerom.daos.UserDaoImpl;
import com.liceu.sromerom.model.User;
import com.liceu.sromerom.utils.HashUtil;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAll(long userid) {
        UserDao ud = new UserDaoImpl();
        List<User> users;
        try {
            users = ud.getAllUsers(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    @Override
    public List<User> getSharedUsers(long noteid) {
        UserDao ud = new UserDaoImpl();
        try {
            return ud.getUsersFromSharedNote(noteid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean validateUser(String username, String password) {
        UserDao ud = new UserDaoImpl();


        try {
            if (ud.existsUserWithUsername(username)) {
                System.out.println(username + " existe...");
                long userid = ud.getUserIdByUsername(username);
                String storedPassword = ud.getUserById(userid).getPassword();
                System.out.println("Stored Password" + storedPassword);
                return HashUtil.validatePassword(password, storedPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean checkRegister(String email, String username, String password, String password2) {
        UserDao ud = new UserDaoImpl();
        Pattern patternPassword = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
        Matcher matcherPassword = patternPassword.matcher(password);
        boolean passwordMatch = matcherPassword.find();

        Pattern patternUsername = Pattern.compile("^(?=[a-zA-Z0-9._]{3,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
        Matcher matcherUsername = patternUsername.matcher(username);
        boolean usernameMatch = matcherUsername.find();

        Pattern patternEmail = Pattern.compile("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
        Matcher matcherEmail = patternEmail.matcher(email);
        boolean emailMatch = matcherEmail.find();

        try {
            System.out.println("correct password? " + passwordMatch);
            System.out.println("correct email? " + emailMatch);
            System.out.println("indenticaly passwords? " + password.equals(password2));
            System.out.println("exists username? " + ud.existsUserWithUsername(username));
            System.out.println("exists email? " + ud.existsUserWithEmail(email));
            if (password.equals(password2) && !ud.existsUserWithUsername(username) && !ud.existsUserWithEmail(email) && passwordMatch && emailMatch && usernameMatch) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public long getUserId(String username) {
        UserDao ud = new UserDaoImpl();
        return ud.getUserIdByUsername(username);
    }

    @Override
    public long getIdByUser(User user) {
        return user.getUserid();
    }

    @Override
    public boolean createUser(String email, String username, String password) {
        try {
            //String generatedSecuredPasswordHash = generateEncryptedPassword(username, password);
            UserDao ud = new UserDaoImpl();

            if (!ud.existsUserWithUsername(username)) {
                String generatedSecuredPasswordHash = HashUtil.generatePasswordHash(password);
                User user = new User(0, email, username, generatedSecuredPasswordHash);
                ud.create(user);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }

    @Override
    public boolean existsUserShare(long noteid, String[] sharedUsers) {
        UserDao ud = new UserDaoImpl();

        try {
            List<User> usersShared = ud.getUsersFromSharedNote(noteid);
            for (User user: usersShared) {
                for (int i = 0; i < sharedUsers.length; i++) {
                    if (user.getUsername().equals(sharedUsers[i])) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }
}
