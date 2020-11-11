package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;

import java.util.List;

public interface NoteDao {
    public List<Note> getAllNotes();
    public List<Note> getAllNotesFromUser(long iduser);
    public Note getNoteById(long id);
    public void create(Note note) throws Exception;
    public void update(Note note) throws Exception;
    public void delete(long idnote) throws Exception;
}
