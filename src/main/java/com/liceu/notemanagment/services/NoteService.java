package com.liceu.notemanagment.services;

import com.liceu.notemanagment.model.Note;

import java.util.List;

public interface NoteService {
    public List<Note> getAll();

    public List<Note> getNotesFromUser(long userid, int offset);

    public List<Note> getCreatedNotes(long id, int offset);

    public long getAllNotesLength(long id);

    public long getCreatedNotesLength(long id);

    public boolean checkFilter(String title, String initDate, String endDate);

    public List<Note> filter(long userid, String type, String title, String initDate, String endDate, int offset);
    public Note getNoteById(long userid, long noteid);

    //public String getTitleById(long noteid);
    //public String getBodyById(long noteid);
    public String getParsedBodyNote(String body);

    public boolean addNote(long userid, String title, String body);

    public boolean editNote(long userid, long idnote, String title, String body);

    public boolean deleteNote(long userid, long idnote);

    //Shared Notes Services

    public long getSharedNoteId(long noteid);

    public List<Note> getSharedNoteWithMe(long userid, int offset);

    public List<Note> getSharedNotes(long userid, int offset);

    public long getLengthSharedNoteWithMe(long userid);

    public long getLengthSharedNotes(long userid);
    //public List<SharedNote> filter(long userid, String title, String initDate, String endDate, int offset);
    public boolean shareNote(long userid, long noteid, String [] usernames);
    public boolean deleteShareNote(long userid, long noteid, long sharedNoteId);

}
