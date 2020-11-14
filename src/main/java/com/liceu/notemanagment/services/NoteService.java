package com.liceu.notemanagment.services;
import com.liceu.notemanagment.model.Note;
import java.util.List;

public interface NoteService {
    public List<Note> getAll();
    public List<Note> getNotesFromUser(long id, int offset);
    public List<Note> getCreatedNotes(long id, int offset);
    public long getAllNotesLength(long id);
    public long getCreatedNotesLength(long id);
    public boolean checkFilter(String title, String initDate, String endDate);
    public List<Note> filter(long userid, String type, String title, String initDate, String endDate, int offset);
    public Note getNoteById(long id);
    public String getTitleById(long noteid);
    public String getBodyById(long noteid);
    public boolean addNote(long iduser, String title, String body);
    public boolean editNote(long idnote, String title, String body);
    public boolean deleteNote(long idnote);
}
