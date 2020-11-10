package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;

import java.util.List;

public interface NoteDao {
    public List<Note> getAllNotes();
    public List<Note> getAllNotesFromUser(long iduser);
    public Note getNoteById(long id);
    public boolean create(Note note);
    public boolean update(Note note);
    public boolean delete(long idnote);
}
