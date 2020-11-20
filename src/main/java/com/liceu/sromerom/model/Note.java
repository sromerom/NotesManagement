package com.liceu.sromerom.model;


public class Note {
    private long noteid;
    private User user;
    private String title;
    private String body;
    private String creationDate;
    private String lastModification;

    public Note(long idnote, User user, String title, String body, String creationDate, String lastModification) {
        this.setNoteid(idnote);
        this.setUser(user);
        this.setTitle(title);
        this.setBody(body);
        this.setCreationDate(creationDate);
        this.setLastModification(lastModification);
    }

    @Override
    public String toString() {
        return "Note{" +
                "idnote=" + noteid +
                ", user=" + user +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", lastModification='" + lastModification + '\'' +
                '}';
    }

    public long getNoteid() {
        return noteid;
    }

    public void setNoteid(long noteid) {
        this.noteid = noteid;
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
