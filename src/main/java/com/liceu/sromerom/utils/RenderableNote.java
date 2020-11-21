package com.liceu.sromerom.utils;

import com.liceu.sromerom.model.User;

import java.util.List;

public class RenderableNote {
    private long noteid;
    private User owner;
    private List<User> sharedUsers;
    private String title;
    private String body;
    private String creationDate;
    private String lastModification;

    public RenderableNote(long noteid, User noteOwner, List<User> sharedUsers, String title, String body, String creationDate, String lastModification) {
        this.noteid = noteid;
        this.owner = noteOwner;
        this.sharedUsers = sharedUsers;
        this.title = title;
        this.body = body;
        this.creationDate = creationDate;
        this.lastModification = lastModification;
    }

    @Override
    public String toString() {
        return "RenderableNote{" +
                "noteid=" + noteid +
                ", owner=" + owner +
                ", sharedUsers=" + sharedUsers +
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<User> getSharedUsers() {
        return sharedUsers;
    }

    public void setSharedUsers(List<User> sharedUsers) {
        this.sharedUsers = sharedUsers;
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
