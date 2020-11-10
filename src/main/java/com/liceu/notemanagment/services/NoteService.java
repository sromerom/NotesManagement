package com.liceu.notemanagment.services;

import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.User;

import java.util.Date;
import java.util.List;

public interface NoteService {
    public List<Note> getAll();
    public List<Note> getNotesFromUser(long id);
    public boolean addNote(long iduser, String title, String body);
    public boolean editNote(long idnote, String title, String body);
    public boolean deleteNote(String title, String body);
}
