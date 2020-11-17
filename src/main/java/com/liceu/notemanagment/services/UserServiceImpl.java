package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.UserDao;
import com.liceu.notemanagment.daos.UserDaoImpl;
import com.liceu.notemanagment.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAll(long userid) {
        UserDao ud = new UserDaoImpl();
        List<User> users = null;
        try {
            users = ud.getAllUsers(userid);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return users;
    }

    @Override
    public boolean validateUser(String username, String password) {
        UserDao ud = new UserDaoImpl();
        if (ud.existsUserWithUsername(username)) {
            System.out.println(username + " existe...");
            long userid = ud.getUserIdByUsername(username);
            String storedPassword = ud.getUserById(userid).getPassword();
            System.out.println("Stored Password" + storedPassword);
            try {
                return HashUtil.validatePassword(password, storedPassword);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                return false;
            } catch (InvalidKeySpecException e) {
                e.printStackTrace();
                return false;
            }
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
        return user.getIduser();
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
            /*
            if (!ud.existsUserWithUsername(username)) {
                User user = new User(0, email, username, generatedSecuredPasswordHash);
                ud.create(user);
                return true;
            } else {
                return false;
            }
             */
            //User user = new User(0, email, username, generatedSecuredPasswordHash);
            //ud.create(user);
        } catch (Exception e) {
            return false;
        }
        return false;
    }
}
