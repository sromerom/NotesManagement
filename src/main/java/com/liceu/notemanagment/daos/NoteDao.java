package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;

import java.util.List;

public interface NoteDao {
    public List<Note> getAllNotes();
    public List<Note> getAllNotesFromUser(long userid, int limit, int offset) throws Exception;
    public long getNotesLengthFromUser(long userid) throws Exception;
    public List<Note> filterByTitle(long userid, String titol) throws Exception;
    public List<Note> filterByDate(long userid, String initDate, String endDate) throws Exception;
    List<Note> filterAll(long userid, String title, String initDate, String endDate) throws Exception;
    public Note getNoteById(long id);
    public void create(Note note) throws Exception;
    public void update(Note note) throws Exception;
    public void delete(long idnote) throws Exception;
}
