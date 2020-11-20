package com.liceu.sromerom.utils;

import com.liceu.sromerom.model.User;

public class RenderableNote {
    private long noteid;
    private User noteOwner;
    private User sharedUser;
    private String title;
    private String body;
    private String creationDate;
    private String lastModification;

    public RenderableNote(long noteid, User noteOwner, User sharedUser, String title, String body, String creationDate, String lastModification) {
        this.noteid = noteid;
        this.noteOwner = noteOwner;
        this.sharedUser = sharedUser;
        this.title = title;
        this.body = body;
        this.creationDate = creationDate;
        this.lastModification = lastModification;
    }

    public long getNoteid() {
        return noteid;
    }

    public void setNoteid(long noteid) {
        this.noteid = noteid;
    }

    public User getNoteOwner() {
        return noteOwner;
    }

    public void setNoteOwner(User noteOwner) {
        this.noteOwner = noteOwner;
    }

    public User getSharedUser() {
        return sharedUser;
    }

    public void setSharedUser(User sharedUser) {
        this.sharedUser = sharedUser;
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
