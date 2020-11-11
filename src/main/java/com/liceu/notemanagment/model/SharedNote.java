package com.liceu.notemanagment.model;

public class SharedNote {
    private User user;
    private Note note;

    public SharedNote( Note note, User user) {
        this.note = note;
        this.user = user;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SharedNote{" +
                "user=" + user +
                ", note=" + note +
                '}';
    }
}
