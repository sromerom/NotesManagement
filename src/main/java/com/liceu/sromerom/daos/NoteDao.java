package com.liceu.sromerom.daos;

import com.liceu.sromerom.model.Note;
import com.liceu.sromerom.model.User;

import java.util.List;

public interface NoteDao {

    public List<Note> getAllNotesFromUser(long userid, int limit, int offset) throws Exception;

    public List<Note> getCreatedNotesFromUser(long userid, int limit, int offset) throws Exception;

    public long getNotesLengthFromUser(long userid) throws Exception;

    public List<Note> filterCreatedNotesBySearch(long userid, String search, int limit, int offset) throws Exception;

    public List<Note> filterCreatedNotesByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception;

    public List<Note> filterAllCreatedNotes(long userid, String search, String initDate, String endDate, int limit, int offset) throws Exception;

    public List<Note> filterTypeOfNoteBySearch(long userid, String search, int limit, int offset) throws Exception;

    public List<Note> filterTypeOfNoteByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception;

    public List<Note> filterAllTypeOfNote(long userid, String search, String initDate, String endDate, int limit, int offset) throws Exception;

    public Note getNoteById(long noteid) throws Exception;

    public boolean isOwnerNote(long userid, long noteid) throws Exception;

    public void create(Note note) throws Exception;

    public void update(Note note) throws Exception;

    public void delete(List<Note> notes) throws Exception;

    //Shared Notes
    public List<Note> getSharedNotesWithMe(long userid, int limit, int offset) throws Exception;

    public List<Note> getSharedNotes(long userid, int limit, int offset) throws Exception;

    public boolean sharedNoteExists(long userid, long noteid) throws Exception;


    public long getSharedNoteId(long noteid) throws Exception;

    public long getSharedNotesWithMeLength(long userid) throws Exception;

    public long getSharedNotesLength(long userid) throws Exception;


    public List<Note> filterSharedNotesWithMeBySearch(long userid, String search, int limit, int offset) throws Exception;

    public List<Note> filterSharedNotesWithMeByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception;

    List<Note> filterAllSharedNotesWithMe(long userid, String search, String initDate, String endDate, int limit, int offset) throws Exception;

    public List<Note> filterSharedNotesBySearch(long userid, String search, int limit, int offset) throws Exception;

    public List<Note> filterSharedNotesByDate(long userid, String initDate, String endDate, int limit, int offset) throws Exception;

    public List<Note> filterAllSharedNotes(long userid, String search, String initDate, String endDate, int limit, int offset) throws Exception;

    public void createShare(Note noteForShare, List<User> users) throws Exception;

    public void deleteShare(Note noteForShare, List<User> users) throws Exception;

    public void deleteAllSharesByNoteId(long noteid) throws Exception;


}
