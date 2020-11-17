package com.liceu.notemanagment.model;


public class Note {
    private long idnote;
    private User user;
    private String title;
    private String body;
    private String creationDate;
    private String lastModification;

    public Note(long idnote, User user, String title, String body, String creationDate, String lastModification) {
        this.setIdnote(idnote);
        this.setUser(user);
        this.setTitle(title);
        this.setBody(body);
        this.setCreationDate(creationDate);
        this.setLastModification(lastModification);
    }

    @Override
    public String toString() {
        return "Note{" +
                "idnote=" + idnote +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", lastModification='" + lastModification + '\'' +
                '}';
    }

    public long getIdnote() {
        return idnote;
    }

    public void setIdnote(long idnote) {
        this.idnote = idnote;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getLastModification() {
        return lastModification;
    }

    public void setLastModification(String lastModification) {
        this.lastModification = lastModification;
    }

}
