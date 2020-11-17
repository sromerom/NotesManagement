package com.liceu.notemanagment.daos;

import com.liceu.notemanagment.model.Note;
import com.liceu.notemanagment.model.User;

import java.util.List;

public interface NoteDao {

    //Created Notes
    public List<Note> getAllNotes();

    public List<Note> getAllNotesFromUser(long userid, int limit, int offset) throws Exception;

    public List<Note> getCreatedNotesFromUser(long userid, int limit, int offset) throws Exception;

    public long getNotesLengthFromUser(long userid) throws Exception;

    public List<Note> filterByTitle(long userid, String titol, int limit, int offset) throws Exception;

    public List<Note> filterByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception;

    List<Note> filterAll(long userid, String title, String initDate, String endDate, int limit, int offset) throws Exception;

    List<Note> filterByTitleAllTypeNotes(long userid, String titol, int limit, int offset) throws Exception;

    List<Note> filterByDateAllTypeNotes(long userid, String initDate, String endDate, int limit, int offset) throws Exception;

    List<Note> filterAllAllTypeNotes(long userid, String title, String initDate, String endDate, int limit, int offset) throws Exception;

    public Note getNoteById(long userid, long noteid) throws Exception;

    public void create(Note note) throws Exception;

    public void update(Note note) throws Exception;

    public void delete(long idnote) throws Exception;

    //Shared Notes
    public List<Note> getSharedNotesWithMe(long userid, int limit, int offset) throws Exception;

    public List<Note> getSharedNotes(long userid, int limit, int offset) throws Exception;

    public boolean sharedNoteExists(long userid, long noteid) throws Exception;

    public long[] getSharedNoteById(long shareNoteId) throws Exception;

    public long getSharedNoteId(long noteid) throws Exception;

    public long getSharedNotesWithMeLength(long userid) throws Exception;

    public long getSharedNotesLength(long userid) throws Exception;


    public List<Note> filterSharedNotesWithMeByTitle(long userid, String titol, int limit, int offset) throws Exception;

    public List<Note> filterSharedNotesWithMeByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception;

    List<Note> filterSharedNotesWithMeAll(long userid, String title, String initDate, String endDate, int limit, int offset) throws Exception;

    public void createShare(Note noteForShare, List<User> users) throws Exception;

    public void deleteShare(long sharedNoteId) throws Exception;

}
