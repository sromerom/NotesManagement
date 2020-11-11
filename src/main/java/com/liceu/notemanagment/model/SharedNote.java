package com.liceu.notemanagment.model;

public class SharedNote {
    private long idShareNote;
    private User user;
    private Note note;

    public SharedNote(long idShareNote, Note note, User user) {
        this.idShareNote = idShareNote;
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
                "idShareNote=" + idShareNote +
                ", user=" + user +
                ", note=" + note +
                '}';
    }

    public long getIdShareNote() {
        return idShareNote;
    }

    public void setIdShareNote(long idShareNote) {
        this.idShareNote = idShareNote;
    }
}
