package com.liceu.sromerom.services;

import com.liceu.sromerom.model.User;

import java.util.List;

public interface UserService {
    List<User> getAll(long userid);

    List<User> getSharedUsers(long noteid);

    User getUserById(long userid);

    boolean createUser(String email, String username, String password);

    boolean existsUserShare(long noteid, String[] sharedUsers);

    boolean validateUser(String username, String password);

    boolean checkRegister(String email, String username, String password, String password2);

    boolean checkEditData(long userid, String email, String username);

    boolean checkPasswordData(long userid, String currentPassword, String password, String password2);

    boolean editPassword(long userid, String password);

    boolean editDataInfo(long userid, String email, String username);

    long getUserId(String username);
}
