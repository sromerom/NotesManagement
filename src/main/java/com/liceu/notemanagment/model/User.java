package com.liceu.notemanagment.model;

public class User {
    private long iduser;
    private String email;
    private String username;
    private String password;

    public User(long iduser, String email, String username, String password) {
        this.setIduser(iduser);
        this.setEmail(email);
        this.setUsername(username);
        this.setPassword(password);
    }


    public long getIduser() {
        return iduser;
    }

    public void setIduser(long iduser) {
        this.iduser = iduser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "iduser=" + iduser +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
