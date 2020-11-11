package com.liceu.notemanagment.services;

import com.liceu.notemanagment.daos.UserDao;
import com.liceu.notemanagment.daos.UserDaoImpl;
import com.liceu.notemanagment.model.User;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAll() {
        UserDao ud = new UserDaoImpl();
        List<User> users = ud.getAllUsers();
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
            return validatePassword(password, storedPassword);
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
            String generatedSecuredPasswordHash = generateEncryptedPassword(username, password);
            UserDao ud = new UserDaoImpl();
            /*
            if (!ud.existsUserWithUsername(username)) {
                User user = new User(0, email, username, generatedSecuredPasswordHash);
                ud.create(user);
                return true;
            } else {
                return false;
            }
             */
            User user = new User(0, email, username, generatedSecuredPasswordHash);
            ud.create(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private static String generateEncryptedPassword(String username, String password) {
        int iterations = 1000;
        char[] chars = password.toCharArray();
        byte[] salt = username.getBytes();

        try {
            PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 128);
            SecretKeyFactory skf = null;
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            System.out.println(iterations + ":" + toHex(salt) + ":" + toHex(hash));
            return iterations + ":" + toHex(salt) + ":" + toHex(hash);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean validatePassword(String originalPassword, String storedPassword) {
        try {
            String[] parts = storedPassword.split(":");
            int iterations = Integer.parseInt(parts[0]);
            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);

            PBEKeySpec spec = new PBEKeySpec(originalPassword.toCharArray(), salt, iterations, 128);
            SecretKeyFactory skf = null;
            skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] testHash = skf.generateSecret(spec).getEncoded();

            int diff = hash.length ^ testHash.length;
            for (int i = 0; i < hash.length && i < testHash.length; i++) {
                diff |= hash[i] ^ testHash[i];
            }
            return diff == 0;
        } catch (Exception e) {
            return false;
        }
    }

    private static byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }

    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }
}
